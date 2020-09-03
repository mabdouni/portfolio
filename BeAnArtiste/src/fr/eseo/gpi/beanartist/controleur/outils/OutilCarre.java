package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.modele.formes.Carre;
import fr.eseo.gpi.beanartist.modele.formes.Point;
import fr.eseo.gpi.beanartist.vue.formes.VueCarre;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilCarre extends OutilForme{

	public OutilCarre(PanneauDessin panneauDessin) {
		super(panneauDessin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VueForme creerVueForme() {
		// TODO Auto-generated method stub
		VueForme vueForme ;
		double longeur = 0;
		
		if (Math.abs(getFin().getX() - getDebut().getX())>Math.abs(getFin().getY() - getDebut().getY()))
			longeur = Math.abs(getFin().getX() - getDebut().getX());
		else 
			longeur = Math.abs(getFin().getY() - getDebut().getY());
		
		if (getDebut().getX()==getFin().getX() && getDebut().getY()== getFin().getY()) // double clique
			vueForme = new VueCarre(new Carre(getDebut()));
		else if (getFin().getX() - getDebut().getX() < 0 && getFin().getY() - getDebut().getY() < 0) 
			vueForme = new VueCarre(new Carre(getFin(), longeur));
		else if (getFin().getY() - getDebut().getY() < 0) 
			vueForme = new VueCarre(new Carre(new Point(getDebut().getX(), getFin().getY()),longeur));
		else if (getFin().getX() - getDebut().getX() < 0) 
			vueForme = new VueCarre(new Carre(new Point(getFin().getX(), getDebut().getY()),longeur));
		else 
			vueForme = new VueCarre(new Carre(getDebut(), longeur));
		return vueForme;
	}	
}
