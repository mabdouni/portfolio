package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.modele.formes.Cercle;
import fr.eseo.gpi.beanartist.modele.formes.Point;
import fr.eseo.gpi.beanartist.vue.formes.VueCercle;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilCercle extends OutilForme{

	public OutilCercle(PanneauDessin panneauDessin) {
		super(panneauDessin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VueForme creerVueForme() {
		// TODO Auto-generated method stub
		double largeur=this.getFin().getX() - this.getDebut().getX();
		double hauteur=this.getFin().getY() - this.getDebut().getY();
		double diametre=Math.max(largeur, hauteur);
		Cercle cercle;
		if (this.getFin().getX() == this.getDebut().getX() && this.getFin().getY() == this.getDebut().getY()) {
			cercle = new Cercle(this.getDebut());
		} else {
			if (largeur > 0 && hauteur> 0) {
				cercle = new Cercle(this.getDebut(),diametre);
			} else if (largeur < 0 && hauteur > 0) {
				cercle = new Cercle(new Point(this.getDebut().getX() + largeur, this.getDebut().getY()),Math.abs(diametre));
			} else if (largeur > 0 && hauteur < 0) {
				cercle = new Cercle(new Point(this.getDebut().getX(),this.getDebut().getY() + hauteur),Math.abs(diametre));
			} else {
				cercle = new Cercle(this.getFin(), Math.abs(diametre));
			}
			
		}
		return new VueCercle(cercle);
	}

}
