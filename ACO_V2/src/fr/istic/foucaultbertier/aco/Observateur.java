package fr.istic.foucaultbertier.aco;

/**
 * Un observateur est un objet souhaitant être notifié à chaque fois qu'un objet Observable auprès duquel il se sera "inscrit" est modifié
 * @see Observable
 */
public  interface Observateur 
{
	/**
	 * Cette méthode est executée lorsque l'objet Observable fait appel à sa méthode notifierObservateurs().
	 * @param o L'objet Observable se passant lui même en paramètre, permettant à l'Observateur de se mettre à jour
	 */
	public void miseAJour(Observable o) ;

}

