package fr.eseo.gpi.beanartist.modele.formes;

public class Cercle extends Ellipse{
	public Cercle(){
		super ();
	}
	public Cercle (Point position){
		super (position);
	}
	public Cercle (double taille){
		super (taille,taille);		
	}
	public Cercle (double abs, double ord, double taille){
		super (abs,ord,taille,taille);
	}
	public Cercle (Point position,double taille){
		super (position,taille,taille);
	}
	public void setHauteur(double hauteur){
		super.setHauteur(hauteur);
		super.setLargeur(hauteur);
	}
	public void setLargeur(double largeur){
		super.setHauteur(largeur);
		super.setLargeur(largeur);
	}
	public double perimetre(){
		double d = getHauteur() ;		
		return (double) Math.round( Math.PI * d * 100) / 100;
	}
}
