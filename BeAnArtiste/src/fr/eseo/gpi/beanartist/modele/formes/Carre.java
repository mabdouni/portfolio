package fr.eseo.gpi.beanartist.modele.formes;

public class Carre extends Rectangle {
	public Carre(){
		super ();
	}
	public Carre (Point position){
		super (position);
	}
	public Carre (double taille){
		super (taille,taille);
	}
	public Carre (double abs, double ord, double taille){
		super (abs,ord,taille,taille);
	}
	public Carre (Point position,double taille){
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
}