package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.vue.ui.FenetreBeAnArtist;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilCercleTest {
	public static void main(String[] args) {
		FenetreBeAnArtist fenetre = FenetreBeAnArtist.getInstance();
		fenetre.setVisible(true);
		PanneauDessin monPanneauDessin = fenetre.getPanneauDessin();
		OutilCercle controleur = new OutilCercle(monPanneauDessin);
		controleur.associer(monPanneauDessin);
	}
}
