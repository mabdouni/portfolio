package fr.eseo.gpi.beanartist.controleur.outils;

import java.awt.event.MouseEvent;

import fr.eseo.gpi.beanartist.modele.formes.Point;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public abstract class OutilForme extends Outil {

	public OutilForme(PanneauDessin panneauDessin) {
		super(panneauDessin);
		// TODO Auto-generated constructor stub
	}
	public void mouseReleased(MouseEvent event) {
		setFin(new Point(event.getX(), event.getY()));
		if (!(getDebut().getX()==getFin().getX() && getDebut().getY()== getFin().getY())) {
			getPanneauDessin().ajouterVueForme(creerVueForme());
			getPanneauDessin().repaint();
		}
	}
	public void mousePressed(MouseEvent event) {
		setDebut(new Point(event.getX(), event.getY()));
	}
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2) {
			VueForme vueForme = creerVueForme();
			getPanneauDessin().ajouterVueForme(vueForme);
			getPanneauDessin().repaint();
		}
	}
	public void mouseDragged(MouseEvent event) {

	}
	protected abstract VueForme creerVueForme();
}
