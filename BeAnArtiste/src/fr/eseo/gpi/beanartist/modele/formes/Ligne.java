package fr.eseo.gpi.beanartist.modele.formes;

import java.text.DecimalFormat;

public class Ligne extends Forme {

	public static final double EPSILON = 1e-7d;

	public Ligne (){
		super();
	}
	public Ligne(Point position){
		super(position);
	}
	public Ligne(double largeur,double hauteur){
		super(largeur,hauteur);
	}
	public Ligne(Point position, double largeur, double hauteur){
		super(position,largeur,hauteur);
	}
	public Ligne(double abs,double ord ,double largeur, double hauteur){
		super(abs,ord,largeur,hauteur);
	}
	
	public Point getP1(){
		return getPosition();
	}
	public Point getP2(){
		return (new Point(getX()+getLargeur(), getY()+getHauteur()));
	}
	public void setP1(Point position){
		if (getP1().getX() != position.getX())
			setLargeur(getP2().getX() - position.getX());
		if (getP1().getY() != position.getY())
			setHauteur(getP2().getY() - position.getY());
		setPosition(position);
	}	
	public void setP2(Point position){
		setHauteur(position.getY()-getPosition().getY());
		setLargeur(position.getX()-getPosition().getX());
	}
	public double getCadreMinX(){
		if (getP1().getX()<=getP2().getX())
			return getP1().getX();
		else
			return getP2().getX();
	}
	public double getCadreMaxX(){
		if (getP1().getX()>getP2().getX())	
			return getP1().getX();
		else
			return getP2().getX();
	}
	public double getCadreMinY(){
		if (getP1().getY()<=getP2().getY())
			return getP1().getY();
		else
			return getP2().getY();
	}
	public double getCadreMaxY(){
		if (getP1().getY()>getP2().getY())
			return getP1().getY();
		else
			return getP2().getY();
	}
	@Override
	public double aire() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double perimetre() {
		// TODO Auto-generated method stub
		return Math.sqrt(Math.pow(getHauteur(), 2) + Math.pow(getLargeur(), 2));
	}
	
	@Override
	public boolean contient(Point position) {
		// TODO Auto-generated method stub
		if (distance(position, getPosition()) + distance(getP2(), position) - perimetre() <= EPSILON)
			return true;
		return false;
	}

	@Override
	public boolean contient(double abs, double ord) {
		// TODO Auto-generated method stub
		return contient(new Point (abs,ord));
	}
	public String toString(){
		DecimalFormat decimal = new DecimalFormat("0.##");
		return "[Ligne]" +" p1 : "+ getP1() +" p2 : "+ getP2() + " longueur : " + decimal.format(perimetre());
	}
	private double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
	}

}
