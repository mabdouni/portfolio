package fr.eseo.gpi.beanartist.vue.formes;

import java.awt.Graphics2D;

import fr.eseo.gpi.beanartist.modele.formes.Ellipse;

public class VueEllipse extends VueForme{

	public VueEllipse(Ellipse ellipse) {
		super(ellipse);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void affiche(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawOval((int) this.getForme().getX(),(int) this.getForme().getY()
 			,(int) this.getForme().getLargeur(),(int) this.getForme().getHauteur());
	}
	
}
