package fr.eseo.gpi.beanartist.modele.formes;

import java.text.DecimalFormat;

public abstract class Forme {
	public static final double LARGEUR_PAR_DEFAUT = 100;
	public static final double HAUTEUR_PAR_DEFAUT = 300;
	private Point position;
	private double hauteur;
	private double largeur;

	public Forme(){
		this(0.0,0.0,LARGEUR_PAR_DEFAUT,HAUTEUR_PAR_DEFAUT);
	}
	public Forme(Point position){
		this(position.getX(),position.getY(),LARGEUR_PAR_DEFAUT, HAUTEUR_PAR_DEFAUT);
	}
	public Forme(double largeur,double hauteur){
		this(0.0,0.0,largeur, hauteur);
	}
	public Forme(double abs, double ord,double largeur,double hauteur){
		setPosition(new Point (abs,ord));
		setLargeur(largeur);
		setHauteur(hauteur);
	}
	public Forme(Point position,double largeur,double hauteur){
		this(position.getX(),position.getY(),largeur, hauteur);
	}
	public Point getPosition(){
		return position;
	}
	public double getX(){
		return position.getX();
	}
	public double getY(){
		return position.getY();
	}
	public double getHauteur(){
		return hauteur;
	}
	public double getLargeur(){
		return largeur;
	}
	public double getCadreMinX(){
		return position.getX();
	}
	public double getCadreMinY(){
		return position.getY();
	}
	public double getCadreMaxX(){
		return position.getX()+getLargeur();
	}
	public double getCadreMaxY(){
		return position.getY()+getHauteur();
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public void	setX(double abs){	
		this.position.setX(abs);
	}
	public void	setY(double ord){	
		this.position.setY(ord);
	}
	public void setHauteur(double hauteur){
		this.hauteur = hauteur;
	}
	public void setLargeur(double largeur){
		this.largeur = largeur;
	}
	public void deplacerVers(double nX, double nY){
		this.position.setX(nX);
		this.position.setY(nY);
	}
	public void deplacerDe(double dX,double dY){
		this.position.setX(position.getX()+dX);
		this.position.setY(position.getY()+dY);
	}
	public abstract double aire();
	public abstract double perimetre();
	public abstract boolean contient(Point position);
	public abstract boolean contient(double abs, double ord);
	public String toString() {
		DecimalFormat decimal = new DecimalFormat("0.##");
		return "[" + getClass().getSimpleName()+ "]"+ " pos " + getPosition().toString() + " dim " + decimal.format(getLargeur()) + " x " + decimal.format(getHauteur()) + " périmètre : " + decimal.format(perimetre()) + " aire : " + decimal.format(aire());
	}
}
