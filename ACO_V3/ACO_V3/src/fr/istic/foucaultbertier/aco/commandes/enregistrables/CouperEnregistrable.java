package fr.istic.foucaultbertier.aco.commandes.enregistrables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.istic.foucaultbertier.aco.Enregistreur;
import fr.istic.foucaultbertier.aco.commandes.Couper;
import fr.istic.foucaultbertier.aco.mementos.MementoCommande;
import fr.istic.foucaultbertier.aco.mementos.MementoCouper;
import fr.istic.foucaultbertier.aco.moteur.MoteurEdition;

/**
 * La classe CouperEnregistrable execute une commande Couper et enregistre son MementoCommande dans un Enregistreur
 * @see Enregistreur
 * @see Couper
 * @see CommandeEnregistrable
 */
public final class CouperEnregistrable implements CommandeEnregistrable {


	/**
	 * Logger pour suivre le déroulement de l'application
	 */
	private static final Logger LOGGER = LogManager.getLogger(CouperEnregistrable.class.getName());
	
	private Enregistreur enregistreur;
	private MoteurEdition moteur;
	
	/**
	 * Créé une commande CouperEnregistrable
	 * L'ensemble des paramètres doit être renseigné
	 * @param moteur Le MoteurEdition auquel adresser la commande
	 * @param enregistreur L'enregsitreur de commande
	 */
	public CouperEnregistrable(MoteurEdition moteur, Enregistreur enregistreur){
		
		/* Préconditions */
		if(enregistreur == null){
			
			throw new IllegalArgumentException("enregistreur est à null");
		}
		if(moteur == null){
			
			throw new IllegalArgumentException("moteur est à null");
		}
		
		/*Traitement*/
		
		this.enregistreur = enregistreur;
		this.moteur = moteur;
	}
	
	/**
	 * Créé une Commande CouperEnregistrable à partir d'un MementoCouper et execute une commande Couper
	 * @param memento Le memento duquel on restaure l'état de la commande enregistrable
	 */
	public CouperEnregistrable(MementoCommande memento){
		
		restaurer(memento);	
		new Couper(moteur).executer();
	}
	
	/**
	 * Effectue l'enregistrement de la commande auprès de l'enregistreur et execute la commande auprès du moteur
	 */
	@Override
	public final void executer() {
		
		enregistreur.enregistrer(this);
		LOGGER.trace("Exécution d'une commande CouperEnregistrable");
		new Couper(moteur).executer();
	}

	/**
	 * Retour l'état de l'objet sous forme d'un objet MementoCouper
	 * @see MementoCouper
	 */
	@Override
	public final MementoCommande getMemento() {
		
		return new MementoCouper(moteur, enregistreur);
	}

	/**
	 * Restaure l'état d'une commande à partir d'un memento
	 * @param memento L'objet memento de la classe MementoCouper (non null)
	 * @see MementoCouper
	 */
	@Override
	public final void restaurer(MementoCommande memento) {
		
		/* Préconditions */
		if(memento == null){
			
			throw new IllegalArgumentException("memento est à null");
		}
		
		if(!(memento instanceof MementoCouper)){
			
			throw new IllegalArgumentException("Le memento n'est pas de la classe MementoCouper");
		}
		
		LOGGER.trace("Restauration d'une commande CouperEnregistrable à partir d'un memento");
		
		/*Traitement*/
		this.moteur = memento.getMoteur();
		this.enregistreur = memento.getEnregistreur();
	}
}
