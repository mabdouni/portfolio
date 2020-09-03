package fr.eseo.gpi.beanartist.vue.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import fr.eseo.gpi.beanartist.controleur.outils.Outil;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
public class PanneauDessin extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Outil outilCourant;
	private List<VueForme> vueFormes = new ArrayList<VueForme>();
	public static final int LARGEUR_PAR_DEFAUT=1000;
	public static final int HAUTEUR_PAR_DEFAUT=1000;
	public static final java.awt.Color COULEUR_FOND_PAR_DEFAUT = Color.WHITE;
	public PanneauDessin (int largeur, int hauteur,java.awt.Color fond){
		super();
		this.setPreferredSize(new Dimension(largeur,hauteur));
		this.setBackground(fond);
	}
	public PanneauDessin () {
		super();
		this.setPreferredSize(new Dimension(LARGEUR_PAR_DEFAUT,HAUTEUR_PAR_DEFAUT));
		this.setBackground(COULEUR_FOND_PAR_DEFAUT);
	}
	public List<VueForme> getVueFormes() {
		return this.vueFormes;
	}
	public void ajouterVueForme(VueForme vueForme) {
		if(!vueForme.equals(null))
			vueFormes.add(vueForme);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g.create();
		 for(VueForme vue : vueFormes) {
			 vue.affiche(g2D);
		 }
		 g2D.dispose();
	}
	public void setOutilCourant(Outil outil) {
		this.outilCourant = outil;
	}
	public Outil getOutilCourant() {
		return outilCourant;
	}
	public void setVueFormes(ArrayList<VueForme> liste) {
		this.vueFormes = liste;
	}
}
