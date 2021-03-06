package fr.istic.foucaultbertier.aco.tests;

import fr.istic.foucaultbertier.aco.Observable;
import fr.istic.foucaultbertier.aco.Observateur;
import fr.istic.foucaultbertier.aco.moteur.Buffer;

/**
 * Cette classe, crée exclusivement pour les tests sert à vérifier que les changements effectuées au niveau du moteur d'édition sont bien répercutés au niveau de l'IHM
 *
 */
public final class IHMTest implements Observateur {

	private String derniereInsert;
	
	IHMTest(){
		
		derniereInsert = "";
	}
	
	@Override
	public void miseAJour(Observable o) {
		
		/* Préconditions */
		if(o == null){
			
			throw new IllegalArgumentException("o est à null");
		}
		
		/* Traitement */
		if(o instanceof Buffer){
			
			Buffer buffer = (Buffer) o;
			derniereInsert = buffer.getContenu();
		}
	}
	
	/**
	 * Retourne la dernière mise à jour effectuée par le Buffer
	 * @return La dernière mise à jour effectuée par le Buffer
	 */
	public String getDerniereInsert(){
		
		return new String(derniereInsert);
	}
}
