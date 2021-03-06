package fr.istic.foucaultbertier.aco.ihm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.AbstractDocument;

import fr.istic.foucaultbertier.aco.Enregistreur;
import fr.istic.foucaultbertier.aco.GestionnaireHisto;
import fr.istic.foucaultbertier.aco.Observable;
import fr.istic.foucaultbertier.aco.Observateur;
import fr.istic.foucaultbertier.aco.commandes.Arreter;
import fr.istic.foucaultbertier.aco.commandes.Defaire;
import fr.istic.foucaultbertier.aco.commandes.Demarrer;
import fr.istic.foucaultbertier.aco.commandes.Refaire;
import fr.istic.foucaultbertier.aco.commandes.Rejouer;
import fr.istic.foucaultbertier.aco.commandes.enregistrables.CollerEnregistrable;
import fr.istic.foucaultbertier.aco.commandes.enregistrables.CopierEnregistrable;
import fr.istic.foucaultbertier.aco.commandes.enregistrables.CouperEnregistrable;
import fr.istic.foucaultbertier.aco.commandes.enregistrables.SupTexteEnregistrable;
import fr.istic.foucaultbertier.aco.moteur.Buffer;
import fr.istic.foucaultbertier.aco.moteur.MoteurEdition;

/**
 * Interface graphique de notre éditeur
 * Utilise une JTextArea pour l'affichage du buffer
 */
public final class IHM extends JFrame implements Observateur, ActionListener
{

	private static final long serialVersionUID = 1L;
	
	//Boutons
	private final JButton coller;
	private final JButton copier;
	private final JButton couper;
	private final JButton supprimer;
	private final JButton enregistrer;
	private final JButton jouer;
	private final JButton stop;
	private final JButton defaire;
	private final JButton refaire;
	
	//Zone de texte
	private final JTextArea zoneTexte;

	//Moteur d'édition
	private final MoteurEdition moteur;
	
	//Listener d'insertions
	private final FiltreModifications filtreModifs;
	private final ListenerSelection listenerSelection;
	
	//Enregistreur des mementos des commandes enregistrables
	private final Enregistreur enregistreur;
	
    public IHM(final MoteurEdition moteur, final Enregistreur enregistreur, final GestionnaireHisto gestionnaireHisto){

    	/* Préconditions */
    	if(moteur == null){
    		
    		throw new IllegalArgumentException("moteur est à null");
    	}
    	

    	if(enregistreur == null){
    		
    		throw new IllegalArgumentException("enregistreur est à null");
    	}
    	

    	if(gestionnaireHisto == null){
    		
    		throw new IllegalArgumentException("gestionnaireHisto est à null");
    	}
    	
    	/* Traitement */
    	this.moteur = moteur;
    	this.enregistreur = enregistreur;
    	
    	filtreModifs = new FiltreModifications(moteur, enregistreur);
    	listenerSelection = new ListenerSelection(moteur, enregistreur);
    	
        zoneTexte = new ZoneTexte(15, 80, moteur, enregistreur);
        zoneTexte.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        zoneTexte.setFont(new Font("monospaced", Font.PLAIN, 14));
        zoneTexte.addCaretListener(listenerSelection);
        ((AbstractDocument)zoneTexte.getDocument()).setDocumentFilter(filtreModifs);
        JScrollPane scrollingText = new JScrollPane(zoneTexte);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        
        //Ajout de la barre de défilement
        content.add(scrollingText, BorderLayout.CENTER);

        //Création de la barre d'outils
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(1,1,1,0));

        //Instanciation des boutons
        coller = new JButton();
        copier = new JButton();
        couper = new JButton();
        supprimer = new JButton();
        
        enregistrer = new BoutonEnregistrer();
        jouer = new BoutonJouer();
        stop = new BoutonStop();
        
        enregistreur.ajouterObservateur((Observateur)enregistrer);
        enregistreur.ajouterObservateur((Observateur)jouer);
        enregistreur.ajouterObservateur((Observateur)stop);
        
        defaire = new BoutonDefaire();
        refaire = new BoutonRefaire();
        
        gestionnaireHisto.ajouterObservateur((Observateur)defaire);
        gestionnaireHisto.ajouterObservateur((Observateur)refaire);

        //Association des icones aux boutons
        coller.setIcon(new ImageIcon(getClass().getResource("/icones/coller.png")));
        copier.setIcon(new ImageIcon(getClass().getResource("/icones/copier.png")));
        couper.setIcon(new ImageIcon(getClass().getResource("/icones/couper.png")));
        supprimer.setIcon(new ImageIcon(getClass().getResource("/icones/supprimer.png")));
        enregistrer.setIcon(new ImageIcon(getClass().getResource("/icones/rec.png")));
        jouer.setIcon(new ImageIcon(getClass().getResource("/icones/play.png")));
        stop.setIcon(new ImageIcon(getClass().getResource("/icones/stop.png")));
        defaire.setIcon(new ImageIcon(getClass().getResource("/icones/undo.png")));
        refaire.setIcon(new ImageIcon(getClass().getResource("/icones/redo.png")));

        //Association des bulles d'aide
        coller.setToolTipText("Coller");
        copier.setToolTipText("Copier");
        couper.setToolTipText("Couper");
        supprimer.setToolTipText("Supprimer");
        enregistrer.setToolTipText("Enregistrer");
        jouer.setToolTipText("Jouer");
        stop.setToolTipText("Stop");
        defaire.setToolTipText("Defaire");
        refaire.setToolTipText("Refaire");

        //Spécification des listeners
        coller.addActionListener(this);
        copier.addActionListener(this);
        couper.addActionListener(this);
        supprimer.addActionListener(this);
        enregistrer.addActionListener(this);
        jouer.addActionListener(this);
        stop.addActionListener(this);
        defaire.addActionListener(this);
        refaire.addActionListener(this);

        coller.setFocusable(false);
        copier.setFocusable(false);
        couper.setFocusable(false);
        supprimer.setFocusable(false);
        enregistrer.setFocusable(false);
        jouer.setFocusable(false);
        stop.setFocusable(false);
        defaire.setFocusable(false);
        refaire.setFocusable(false);

        //Ajout des boutons à la barre d'outils
        menuBar.add(copier);
        menuBar.add(couper);
        menuBar.add(coller);
        menuBar.add(supprimer);
        menuBar.add(enregistrer);
        menuBar.add(jouer);
        menuBar.add(stop);
        menuBar.add(defaire);
        menuBar.add(refaire);

        //On ne peut pas jouer une action dès le début
        stop.setEnabled(false);
        jouer.setEnabled(false);
        
        //Mise en place des éléments dans la fenêtre
        setContentPane(content);
        setJMenuBar(menuBar);

        //Ajout des comportements par défaut et des propriété propres à notre éditeur + Affichage
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Editeur de texte v3 [BERTIER - FOUCAULT]");
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    /**
     * @see Observateur
     */
	@Override
	public final void miseAJour(Observable o) {
		
		/* Préconditions */
		if(o == null){
			
			throw new IllegalArgumentException("o est à null");
		}
		
		/* Traitement */
		if(o instanceof Buffer){
			
			Buffer buffer = (Buffer) o;
			
			//On désactive le filtre pour éviter de renvoyer une commande
			filtreModifs.setReagir(false);
			listenerSelection.setReagir(false);
			
			zoneTexte.setText(buffer.getContenu());
			zoneTexte.setCaretPosition(buffer.getOffsetModif());
			
			listenerSelection.setReagir(true);
			filtreModifs.setReagir(true);
		}
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==coller){

			new CollerEnregistrable(moteur, enregistreur).executer();
		}
		else if (e.getSource()==copier){

			new CopierEnregistrable(moteur, enregistreur).executer();
		}
		else if (e.getSource()==couper){

			new CouperEnregistrable(moteur, enregistreur).executer();
		}
		else if (e.getSource()==supprimer){

			new SupTexteEnregistrable(moteur, enregistreur).executer();
		}
		else if (e.getSource()==enregistrer){
			
			new Demarrer(enregistreur).executer();
		}
		else if (e.getSource()==jouer){
			
			new Rejouer(enregistreur).executer();
		}
		else if (e.getSource()==stop){
			
			new Arreter(enregistreur).executer();
		}
		else if(e.getSource()==defaire){
			
			new Defaire(moteur).executer();
		}
		else if(e.getSource()==refaire){
			
			new Refaire(moteur).executer();
		}
	}
}