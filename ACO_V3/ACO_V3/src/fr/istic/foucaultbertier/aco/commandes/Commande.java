package fr.istic.foucaultbertier.aco.commandes;

import  fr.istic.foucaultbertier.aco.moteur.MoteurEdition;

/**
 * On commande est chargée de transmettre un ordre à une classe implémentant l'interface MoteurEdition
 * @see MoteurEdition
 */
public  interface Commande 
{
	/**
	 * Execution de la commande auprès de l'implémentation du moteur d'édition
	 */
	public void executer() ;
}

