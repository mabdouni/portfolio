package fr.eseo.gpi.beanartist.modele.formes;

import java.awt.geom.Ellipse2D;

public class Ellipse extends Forme{
	
	public Ellipse(){
		super ();
	}
	public Ellipse (Point position){
		super (position);
	}
	public Ellipse (double largeur, double hauteur){
		super (largeur,hauteur);		
	}
	public Ellipse (double abs, double ord, double largeur, double hauteur){
		super (abs,ord,largeur,hauteur);
	}
	public Ellipse (Point position,double largeur, double hauteur){
		super (position,largeur,hauteur);
	}
	public void setHauteur(double hauteur){
		if (hauteur >= 0)
			super.setHauteur(hauteur);
		else
			throw new IllegalArgumentException("hauteur < 0");
	}
	public void setLargeur(double largeur){
		if (largeur >= 0)
			super.setLargeur(largeur);
		else
			throw new IllegalArgumentException("largeur < 0");
	}	
	@Override
	public double aire() {
		// TODO Auto-generated method stub
		double aire = (Math.PI*(getHauteur()/2)*(getLargeur()/2));
		return  aire;
	}

	@Override
	public double perimetre() {
		// TODO Auto-generated method stub
		double a = getHauteur()/2;
		double b = getLargeur()/2;
		double h = Math.pow(((a-b)/(a+b)), 2);
		double perimetre = Math.PI*(a+b)*(1+((3*h)/(10+Math.sqrt(4-3*h))));
		return perimetre;
	}
	public boolean contient(Point position){
		Ellipse2D.Double ellipse = new Ellipse2D.Double(getX(), getY(), getLargeur(), getHauteur());
		if(ellipse.contains(position.getX(),position.getY()))	
			return true;
		else
			return false;
		
	}
	public boolean contient(double abs, double ord){
		Ellipse2D.Double ellipse = new Ellipse2D.Double(getX(), getY(), getLargeur(), getHauteur());
		if(ellipse.contains(abs,ord))	
			return true;
		else
			return false;
	}
}
