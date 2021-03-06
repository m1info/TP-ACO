package fr.istic.foucaultbertier.aco.mementos;

import fr.istic.foucaultbertier.aco.commandes.enregistrables.CopierEnregistrable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.istic.foucaultbertier.aco.Enregistreur;
import fr.istic.foucaultbertier.aco.moteur.MoteurEdition;

/**
 * Cette classe est chargée de stocker l'état d'une commande CopierEnregistrable
 * @see CopierEnregistrable
 * @see MementoCommande
 */
public final class MementoCopier extends MementoCommande{

	private static final Logger LOGGER = LogManager.getLogger(MementoCopier.class.getName());	
	
	public MementoCopier(MoteurEdition moteur, Enregistreur enregistreur){
		
		super(moteur, enregistreur);
		LOGGER.trace("Création d'un MementoCopier");
	}
}