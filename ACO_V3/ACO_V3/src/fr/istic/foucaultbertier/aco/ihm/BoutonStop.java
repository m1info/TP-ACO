package fr.istic.foucaultbertier.aco.ihm;

import javax.swing.JButton;

import fr.istic.foucaultbertier.aco.Enregistreur;
import fr.istic.foucaultbertier.aco.Observable;
import fr.istic.foucaultbertier.aco.Observateur;

/**
 * Ce bouton est chargé de se mettre à jour (au niveau de son état cliquable ou non) à chaque modification de l'enregistreur
 * @see Enregistreur
 */
public class BoutonStop extends JButton implements Observateur {

	private static final long serialVersionUID = -6273371891402818469L;

	/**
	 * @see Observateur
	 */
	@Override
	public void miseAJour(Observable o) {
		
		if(o == null){
			
			throw new IllegalArgumentException("o est à null");
		}
		
		if(!(o instanceof Enregistreur)){
			
			throw new IllegalArgumentException("o n'est pas du type Enregistreur");
		}
		
		Enregistreur enregistreur = (Enregistreur) o;
		
		if(enregistreur.getEnregistrer()){
			
			setEnabled(true);
		}
		else{
			
			setEnabled(false);
		}
	}
}