package fr.istic.foucaultbertier.aco.ihm;

import javax.swing.JButton;

import fr.istic.foucaultbertier.aco.GestionnaireHisto;
import fr.istic.foucaultbertier.aco.Observable;
import fr.istic.foucaultbertier.aco.Observateur;

/**
 * Ce bouton est chargé de se mettre à jour (au niveau de son état cliquable ou non) à chaque modification du gestionnaire d'historique
 * @see GestionnaireHisto
 */
public class BoutonRefaire extends JButton implements Observateur {

	private static final long serialVersionUID = -6273371891402818469L;

	public BoutonRefaire(){
		
		setEnabled(false);
	}
	
	/**
	 * @see Observateur
	 */
	@Override
	public void miseAJour(Observable o) {
		
		if(o == null){
			
			throw new IllegalArgumentException("o est à null");
		}
		
		if(!(o instanceof GestionnaireHisto)){
			
			throw new IllegalArgumentException("o n'est pas du type GestionnaireHisto");
		}
		
		GestionnaireHisto gestionnaire = (GestionnaireHisto) o;
		
		if(gestionnaire.peutRefaire()){
			
			setEnabled(true);
		}
		else{
			
			setEnabled(false);
		}
	}
}
