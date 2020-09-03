package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.vue.ui.FenetreBeAnArtist;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilLigneTest {
	public static void main(String[] args) {
		FenetreBeAnArtist fenetre = FenetreBeAnArtist.getInstance();
		fenetre.setVisible(true);
		PanneauDessin monPanneauDessin = fenetre.getPanneauDessin();
		OutilLigne controleur = new OutilLigne(monPanneauDessin);
		controleur.associer(monPanneauDessin);
	}
}
