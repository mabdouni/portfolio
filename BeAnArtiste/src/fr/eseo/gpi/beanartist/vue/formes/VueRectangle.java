package fr.eseo.gpi.beanartist.vue.formes;

import java.awt.Graphics2D;

import fr.eseo.gpi.beanartist.modele.formes.Rectangle;

public class VueRectangle extends VueForme {

	public VueRectangle(Rectangle rectangle) {
		super(rectangle);
	}

	@Override
	public void affiche(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawRect((int) this.getForme().getX(),(int) this.getForme().getY()
		,(int) this.getForme().getLargeur(),(int) this.getForme().getHauteur());
	}
}
