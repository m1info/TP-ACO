package fr.istic.foucaultbertier.aco;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.istic.foucaultbertier.aco.mementos.MementoSysteme;
import fr.istic.foucaultbertier.aco.commandes.Defaire;
import fr.istic.foucaultbertier.aco.commandes.Refaire;

/**
 * Cette classe est chargée de gérer les actions défaire/refaire lorsque l'utilisateur les demandent au travers des commandes portant le même nom
 * @see Defaire
 * @see Refaire
 */
public class GestionnaireHisto implements Observable{

	private static final Logger LOGGER = LogManager.getLogger(GestionnaireHisto.class.getName());	
	
	private Stack<MementoSysteme> defaire;
	private Stack<MementoSysteme> refaire;
	
	private List<Observateur> listeObservateurs;
	
	public GestionnaireHisto(){
		
		defaire = new Stack<MementoSysteme>();
		refaire = new Stack<MementoSysteme>();
		listeObservateurs = new ArrayList<Observateur>();
	}
	
	/**
	 * Rétablit le système à l'état T-1 (T représentant le temps au moment de l'exécution de cette méthode)
	 * @return Un MementoSysteme permettant de rétablir le système à l'état T-1
	 * @see MementoSysteme
	 */
	public MementoSysteme defaire(){
		
		LOGGER.trace("On effectue un undo");
		
		if(defaire.size() > 1){
			
			MementoSysteme memSysteme = defaire.pop();
			refaire.push(memSysteme);
		}
			
		notifierObservateurs();	
		return defaire.peek();
	}
	
	/**
	 * Permet d'annuler une action undo
	 * @return Un MementoSysteme permettant de rétablir le système à l'état T+1 (précédemment T-1)
	 * @see MementoSysteme
	 */
	public MementoSysteme refaire(){
		
		LOGGER.trace("On effectue un redo");
		
		MementoSysteme memSysteme = refaire.pop();
		defaire.push(memSysteme);
		
		notifierObservateurs();
		return memSysteme;
	}
	
	/**
	 * @return True si on peut effectuer une action refaire, False sinon
	 */
	public boolean peutRefaire(){
		
		return !refaire.empty();
	}
	
	/**
	 * @return True si on peut effectuer une action defaire, False sinon
	 */
	public boolean peutDefaire(){
		
		return defaire.size() > 1;
	}
	
	/**
	 * Permet d'ajouter un état du système au gestionnaire d'historique
	 * @param memSysteme Le MementoSysteme contenant l'état du système
	 * @see MementoSysteme
	 */
	public void ajouterElement(MementoSysteme memSysteme){
		
		//Faire un redo n'aurait pas de sens
		if(!refaire.empty()){
			
			refaire.clear();
		}
		
		defaire.push(memSysteme);
		
		System.out.println("Taille de defaire :" + defaire.size());
		notifierObservateurs();
	}
	
	/**
	 * @see Observable
	 */
	@Override
	public void ajouterObservateur(Observateur o) {
	
		if(o == null){
			
			throw new IllegalArgumentException("o est à null");
		}
		
		if(listeObservateurs.contains(o)){
			
			throw new IllegalArgumentException("o est déjà dans la liste des observateurs");
		}
		
		listeObservateurs.add(o);
	}

	/**
	 * @see Observable
	 */
	@Override
	public void notifierObservateurs(){

		for(Observateur o : listeObservateurs){
			
			o.miseAJour(this);
		}
	}

	/**
	 * @see Observable
	 */
	@Override
	public void retirerObservateur(Observateur o) {
		
		if(o == null){
			
			throw new IllegalArgumentException("o est à null");
		}
		
		if(!listeObservateurs.contains(o)){
			
			throw new IllegalArgumentException("o n'est pas dans la liste des observateurs");
		}
		
		listeObservateurs.remove(o);
	}
}
