package fr.eseo.gpi.beanartist.vue.formes;

import java.awt.Graphics2D;

import fr.eseo.gpi.beanartist.modele.formes.Ligne;

public class VueLigne extends VueForme{

	public VueLigne(Ligne ligne) {
		super(ligne);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void affiche(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawLine((int) this.getForme().getX(),(int) this.getForme().getY()
				,(int)  (this.getForme().getX()+this.getForme().getLargeur()),
				(int) (this.getForme().getY()+this.getForme().getHauteur()));
	}

}
