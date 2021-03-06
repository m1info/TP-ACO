package fr.istic.foucaultbertier.aco.commandes.enregistrables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.istic.foucaultbertier.aco.Enregistreur;
import fr.istic.foucaultbertier.aco.commandes.Selectionner;
import fr.istic.foucaultbertier.aco.mementos.MementoCommande;
import fr.istic.foucaultbertier.aco.mementos.MementoSelectionner;
import fr.istic.foucaultbertier.aco.moteur.MoteurEdition;
import fr.istic.foucaultbertier.aco.moteur.Selection;

/**
 * La classe SelectionnerEnregistrable execute une commande Selectionner et enregistre son MementoCommande dans un Enregistreur
 * @see Enregistreur
 * @see Selectionner
 * @see CommandeEnregistrable
 */
public final class SelectionnerEnregistrable implements CommandeEnregistrable {


	/**
	 * Logger pour suivre le déroulement de l'application
	 */
	private static final Logger LOGGER = LogManager.getLogger(SelectionnerEnregistrable.class.getName());
	
	private Enregistreur enregistreur;
	private MoteurEdition moteur;
	private Selection selection;
	
	/**
	 * Créé une commande SelectionnerEnregistrable
	 * L'ensemble des paramètres doit être renseigné
	 * @param moteur Le MoteurEdition auquel adresser la commande
	 * @param enregistreur L'enregsitreur de commande
	 * @param selection La selection à associer à la commande
	 */
	public SelectionnerEnregistrable(MoteurEdition moteur, Enregistreur enregistreur, Selection selection){
		
		/* Préconditions */
		if(enregistreur == null){
			
			throw new IllegalArgumentException("enregistreur est à null");
		}
		if(moteur == null){
			
			throw new IllegalArgumentException("moteur est à null");
		}
		if(selection == null){
			
			throw new IllegalArgumentException("selection est à null");
		}
		
		/*Traitement*/
		
		this.enregistreur = enregistreur;
		this.moteur = moteur;
		this.selection = selection;
	}
	
	/**
	 * Créé une Commande SelectionnerEnregistrable à partir d'un MementoSelectionner et execute une commande Selectionner
	 * @param memento Le memento duquel on restaure l'état de la commande enregistrable
	 */
	public SelectionnerEnregistrable(MementoCommande memento){
		
		restaurer(memento);
		LOGGER.trace("Exécution d'une commande Selectionner");
		new Selectionner(moteur, selection).executer();
	}
	
	/**
	 * Effectue l'enregistrement de la commande auprès de l'enregistreur et execute la commande auprès du moteur
	 */
	@Override
	public final void executer() {
		
		enregistreur.enregistrer(this);
		LOGGER.trace("Exécution d'une commande Selectionner");
		new Selectionner(moteur, selection).executer();
	}

	/**
	 * Retour l'état de l'objet sous forme d'un objet MementoSelectionner
	 * @see MementoSelectionner
	 */
	@Override
	public final MementoCommande getMemento() {
		
		return new MementoSelectionner(moteur, enregistreur, selection);
	}

	/**
	 * Restaure l'état d'une commande à partir d'un memento
	 * @param memento L'objet memento de la classe MementoSelectionner (non null)
	 * @see MementoSelectionner
	 */
	@Override
	public final void restaurer(MementoCommande memento) {
		
		/* Préconditions */
		if(memento == null){
			
			throw new IllegalArgumentException("memento est à null");
		}
		
		if(!(memento instanceof MementoSelectionner)){
			
			throw new IllegalArgumentException("Le memento n'est pas de la classe MementoInsSelectionner");
		}
		
		LOGGER.trace("Restauration d'une commande SelectionnerEnregistrable à partir d'un memento");
		
		/*Traitement*/
		this.moteur = memento.getMoteur();
		this.enregistreur = memento.getEnregistreur();
		this.selection = ((MementoSelectionner)memento).getSelection();
	}
}
