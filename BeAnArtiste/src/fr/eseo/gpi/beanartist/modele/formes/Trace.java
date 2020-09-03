package fr.eseo.gpi.beanartist.modele.formes;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Trace extends Forme {
	private static final double EPSILON = 1e-7d;
	private List<Point> points = new ArrayList<Point>();
	public Trace(Point position1){
		super.setPosition(position1);
		super.setHauteur(0);
		super.setLargeur(0);
		points.add(position1);
	}
	public Trace(Point position1,Point position2){
		super.setPosition(position1);
		super.setHauteur(Math.abs(position1.getY()-position2.getY()));
		super.setLargeur(Math.abs(position1.getX()-position2.getX()));
		this.points.add(position1);
		this.points.add(position2);
	}
	public List<Point> getPoints(){
		return points;
	}
	public void setPosition(Point position){
		if (this.getPosition() != null) {
			double deltaX = position.getX() - this.getPosition().getX();
			double deltaY = position.getY() - this.getPosition().getY();
			this.deplacerDe(deltaX, deltaY);
			this.getPosition().deplacerVers(position.getX(), position.getY());
		} else
			super.setPosition(position);
	}
	public void setX(double x) {
		setPosition(new Point(x, this.getPosition().getY()));
	}

	public void setY(double y) {
		setPosition(new Point(this.getPosition().getX(), y));
	}
	public void setHauteur(double hauteur){
		if (this.getHauteur() != 0) {
			double rapport = hauteur / this.getHauteur();
			for (Point p : points) {
				p.deplacerDe(0,(p.getY() - this.getPosition().getY()) * rapport - (p.getY() - this.getPosition().getY()));
			}
		}
		super.setHauteur(hauteur);
	}
	public void setLargeur(double largeur){
		if (this.getLargeur() != 0) {
			double rapport = largeur / this.getLargeur();
			for (Point p : points) {
				p.deplacerDe((p.getX() - this.getPosition().getX()) * rapport - (p.getX() - this.getPosition().getX()),0);
			}
		}
		super.setLargeur(largeur);
	}
	public void ajouterPoint(Point position){
		points.add(position);
	}
	public void deplacerDe(double dX,double dY){
		Point hold;
		for (int i=0; i<points.size();i++){
			hold = points.get(i);
			points.set(i,new Point(dX + hold.getX() ,dY + hold.getY()));
		}
	}
	public void deplacerVers(double nX,double nY){
		Point holdOrigine, hold;
		holdOrigine = points.get(0);
		double ecartX = nX-holdOrigine.getX();
		double ecartY = nY-holdOrigine.getY();
		points.set(0, new Point(nX,nY));
		for (int i=0; i<points.size();i++){
			hold = points.get(i);
			points.set(i,new Point(hold.getX()+ ecartX , hold.getY() + ecartY));
		}
	}
	public String toString(){
		DecimalFormat decimal = new DecimalFormat("0.##");
		return "[Trace] pos : " + this.getPosition().toString() + " dim : " + decimal.format(this.getLargeur()) + " x "
				+ decimal.format(this.getHauteur()) + " longueur : " + decimal.format(this.perimetre()) + " nbSegments : "
				+ Integer.toString(points.size() - 1);	
	}
	@Override
	public double aire() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double perimetre() {
		// TODO Auto-generated method stub
		double perimetre = 0;
		for (int i = 1; i < points.size(); i++) {
			perimetre += Math.sqrt(Math.pow(points.get(i).getX() - points.get(i - 1).getX(), 2)
					+ Math.pow(points.get(i).getY() - points.get(i - 1).getY(), 2));
		}
		return perimetre;
	}

	@Override
	public boolean contient(Point position) {
		// TODO Auto-generated method stub
		boolean resultat = false;
		for (int i = 0; i < points.size()-1; i++) {
			if ((i == points.size() - 1) && ((distance(position, points.get(i)) + distance(points.get(0), position)
					- distance(points.get(i), points.get(0)) <= EPSILON)))
				resultat = true;
			else if (distance(position, points.get(i)) + distance(points.get(i + 1), position)
					- distance(points.get(i), points.get(i + 1)) <= EPSILON)
				resultat = true;

		}
		return resultat;
	}
	private double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
	}
	@Override
	public boolean contient(double abs, double ord) {
		// TODO Auto-generated method stub
		return contient(new Point(abs, ord));
	}
}
