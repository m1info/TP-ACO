package fr.istic.foucaultbertier.aco.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import fr.istic.foucaultbertier.aco.commandes.Defaire;
import fr.istic.foucaultbertier.aco.commandes.Refaire;
import fr.istic.foucaultbertier.aco.moteur.MoteurEdition;
import fr.istic.foucaultbertier.aco.moteur.MoteurImplementation;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MoteurImplementation.class)
public class TestsUnitairesCommandesv3 {
	
	private MoteurEdition moteur;

	
	@Before
	public void setUp(){
		
		moteur = PowerMockito.mock(MoteurImplementation.class);
	}
	
	@Test
	public void testDefaire() {
		
		Defaire cmd = new Defaire(moteur);
		cmd.executer();
		Mockito.verify(moteur).defaire();
	}
	
	@Test
	public void testRefaire() {
		
		Refaire cmd = new Refaire(moteur);
		cmd.executer();
		Mockito.verify(moteur).refaire();
	}
}