package fr.eseo.gpi.beanartist.modele.formes;

public class Rectangle extends Forme{
	public Rectangle(){
		super ();
	}
	public Rectangle (Point position){
		super (position);
	}
	public Rectangle (double largeur, double hauteur){
		super (largeur,hauteur);		
	}
	public Rectangle (double abs, double ord, double largeur, double hauteur){
		super (abs,ord,largeur,hauteur);	
	}
	public Rectangle (Point position,double largeur, double hauteur){
		super (position,largeur,hauteur);
	}
	public void setHauteur(double hauteur){
		if (hauteur >= 0)
			super.setHauteur(hauteur);
		else
			throw new IllegalArgumentException( "hauteur < 0" );
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
		double aire = getHauteur() * getLargeur();
		return aire;
	}

	@Override
	public double perimetre() {
		// TODO Auto-generated method stub
		double perimetre = 2 * (getHauteur()+getLargeur());
		return perimetre;
	}
	public boolean contient(Point position){
		if ((position.getX() >= getX() && position.getX() <= getX()+ getLargeur())&& (position.getY() >= getY() && position.getY() <= getY()+ getHauteur()))
			return true;
		else
			return false;
	}
	public boolean contient(double abs, double ord){
		if ((abs >= getX() && abs <= getX()+ getLargeur())&& (ord >= getY() && ord <= getY()+ getHauteur()))
			return true;
		else
			return false;
	}
}
