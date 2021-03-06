package fr.istic.foucaultbertier.aco.commandes.enregistrables;

import fr.istic.foucaultbertier.aco.commandes.Commande;
import fr.istic.foucaultbertier.aco.mementos.MementoCommande;
import fr.istic.foucaultbertier.aco.Enregistreur;

/**
 * Cette interface est implémenté par les commandes enregistrables. De façon à sauvegarder et restaurer leurs état, le pattern MementoCommande est utilisé.
 * @author Antoine
 * @see Enregistreur
 * @see MementoCommande
 */
public interface CommandeEnregistrable extends Commande {

		/**
		 * Founit un memento enregistrant l'état de l'objet
		 * @return L'état de l'objet stocké sous forme de MementoCommande
		 * @see MementoCommande
		 */
		public MementoCommande getMemento();
		
		/**
		 * Restaure l'état à partir du memento passé en paramètre
		 * @param memento L'état de l'objet stocké sous forme de mémento
		 */
		public void restaurer(MementoCommande memento);
}
