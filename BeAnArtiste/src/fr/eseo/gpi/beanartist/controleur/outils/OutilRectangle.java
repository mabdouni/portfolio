package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.modele.formes.Point;
import fr.eseo.gpi.beanartist.modele.formes.Rectangle;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.formes.VueRectangle;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilRectangle extends OutilForme{

	public OutilRectangle(PanneauDessin panneauDessin) {
		super(panneauDessin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VueForme creerVueForme() {
		// TODO Auto-generated method stub
		double larg=this.getFin().getX() - this.getDebut().getX();
		double haut=this.getFin().getY() - this.getDebut().getY();
		Rectangle rectangle;
		if (this.getFin().getX() == this.getDebut().getX() && this.getFin().getY() == this.getDebut().getY()) {
			rectangle = new Rectangle(this.getDebut());
		} else {
			if (larg > 0 && haut> 0) {
				rectangle = new Rectangle(this.getDebut(), larg, haut);
			} else if (larg < 0 && haut > 0) {
				rectangle = new Rectangle(new Point(this.getDebut().getX() + larg, this.getDebut().getY()),-larg,haut);
			} else if (larg > 0 && haut < 0) {
				rectangle = new Rectangle(new Point(this.getDebut().getX(),this.getDebut().getY() + haut),larg,-haut);
			} else {
				rectangle = new Rectangle(this.getFin(), -larg,-haut);
			}
			
	}
		return new VueRectangle(rectangle);
	}
}
