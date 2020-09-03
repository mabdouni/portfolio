package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.modele.formes.Ellipse;
import fr.eseo.gpi.beanartist.modele.formes.Point;
import fr.eseo.gpi.beanartist.vue.formes.VueEllipse;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilEllipse extends OutilForme {

	public OutilEllipse(PanneauDessin panneauDessin) {
		super(panneauDessin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VueForme creerVueForme() {
		// TODO Auto-generated method stub
		double largeur=this.getFin().getX() - this.getDebut().getX();
		double hauteur=this.getFin().getY() - this.getDebut().getY();
		Ellipse ellipse;
		if (this.getFin().getX() == this.getDebut().getX() && this.getFin().getY() == this.getDebut().getY()) {
			ellipse = new Ellipse(this.getDebut());
		} else {
			if (largeur > 0 && hauteur> 0) {
				ellipse = new Ellipse(this.getDebut(), largeur, hauteur);
			} else if (largeur < 0 && hauteur > 0) {
				ellipse = new Ellipse(new Point(this.getDebut().getX() + largeur, this.getDebut().getY()),-largeur,hauteur);
			} else if (largeur > 0 && hauteur < 0) {
				ellipse = new Ellipse(new Point(this.getDebut().getX(),this.getDebut().getY() + hauteur),largeur,-hauteur);
			} else {
				ellipse = new Ellipse(this.getFin(),-largeur,-hauteur);
			}			
		}
		return new VueEllipse(ellipse);
	}

}
