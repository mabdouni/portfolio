package fr.eseo.gpi.beanartist.vue.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;

public class FenetreBeAnArtist extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TITRE_PAR_DEFAUT = "BeAnArtist";
	private static FenetreBeAnArtist instance = null ;
	private PanneauDessin panneauDessin = null;
	private PanneauBarreOutils panneauBarreOutils = null;

	
	private FenetreBeAnArtist(){
		panneauDessin = new PanneauDessin(1000,1000,Color.WHITE);
		panneauBarreOutils = new PanneauBarreOutils();
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setTitle(TITRE_PAR_DEFAUT);
		super.getContentPane().add(panneauDessin);
		super.getContentPane().add(panneauBarreOutils,BorderLayout.EAST);
		this.pack();
	}
	public static FenetreBeAnArtist getInstance(){
		if(FenetreBeAnArtist.instance == null){
        	FenetreBeAnArtist.instance = new FenetreBeAnArtist();
        }
		return instance;
	}
	public PanneauDessin getPanneauDessin(){
		return this.panneauDessin;
	}
	public PanneauBarreOutils getPanneauBarreOutils(){
		return this.panneauBarreOutils;
	}
	
}
