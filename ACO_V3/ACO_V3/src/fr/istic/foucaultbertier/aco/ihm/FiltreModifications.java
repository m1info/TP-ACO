package fr.istic.foucaultbertier.aco.ihm;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.istic.foucaultbertier.aco.Enregistreur;
import fr.istic.foucaultbertier.aco.commandes.enregistrables.InsTexteEnregistrable;
import fr.istic.foucaultbertier.aco.commandes.enregistrables.SupTexteEnregistrable;
import fr.istic.foucaultbertier.aco.moteur.MoteurEdition;

/**
 * La classe FiltreModifications sert à filtrer les entrées effectuées dans la JTextArea de l'IHM.
 * Dans un premier temps elle ne transmet pas l'action demandée à la JTextArea mais elle transmet une commande au Moteur d'édition.
 * Suite à la modification du Buffer, l'IHM va être misee à jour (via le pattern Observer) et ce classe autorisera la modifications demandée sur la JTextArea
 */
public final class FiltreModifications extends DocumentFilter {

	private static final Logger LOGGER = LogManager.getLogger(FiltreModifications.class.getName());	
	
	private final MoteurEdition moteur;
	private final Enregistreur enregistreur;
	
	private boolean reagir;
	
	/**
	 * Le constructeur a besoin de savoir quel moteur d'édition spécifier aux commandes
	 * @param moteur	Le Moteur d'édition à renseigner pour les commandes (non null)
	 * @param enregistreur L'enregistreur de commandes (non null)
	 */
	public FiltreModifications(MoteurEdition moteur, Enregistreur enregistreur){
		
		super();
		
		if(moteur == null){
			
			throw new IllegalArgumentException("moteur est à null");
		}
		
		if(enregistreur == null){
			
			throw new IllegalArgumentException("enregistreur est à null");
		}	
		
		this.moteur = moteur;
		this.enregistreur = enregistreur;
		
		reagir = true;
	}

	@Override
	/**
	 * Cette méthode est invoquée lorsqu'on colle une chaîne de caractère dans la JTextArea
	 * @param string La chaîne à insérer
	 */
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

		LOGGER.trace("Entrée dans insertString");
		LOGGER.debug("Insertion de la chaîne : " + string);
		
		if(reagir){
			
			new InsTexteEnregistrable(moteur, enregistreur, string).executer();
		}
		else{
			
			super.insertString(fb, offset, string, attr);
		}
		
		LOGGER.trace("Sortie de insertString");
	}

	@Override
	/**
	 * Cette méthode est invoquée lorsqu'on supprime du texte dans la JTextArea
	 */
	public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
		
		LOGGER.trace("Entrée dans remove");
		LOGGER.debug("Supression de la chaîne en position " + offset + " de longueur " + length);
		
		if(reagir){

			new SupTexteEnregistrable(moteur, enregistreur).executer();
		}
		else{
			
			super.remove(fb, offset, length);
		}
		
		LOGGER.trace("Sortie de remove");
	}
	
	/**
	 * Cette méthode est invoquée lorsqu'on insère un caractère au clavier dans la JTextArea
	 */
	@Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
		
		LOGGER.trace("Entrée dans replace");
		LOGGER.debug("Remplacement de la chaîne en position " + offset + " de longueur " + length + " par : " + string);
		
		
		if(reagir){
			
			new InsTexteEnregistrable(moteur, enregistreur, string).executer();
		}
		else{
			
			super.replace(fb, offset, length, string, attrs);
		}
		
		LOGGER.trace("Sortie de replace");
	}
	
	/**
	 * Indique au filtre s'il doit ou non lancer une commande lorsqu'il est notifié
	 * @param reagir Un booleen spécifiant s'il faut ou non réagir aux évènements reçus
	 */
	public void setReagir(boolean reagir){
		
		this.reagir = reagir;
	}
}
