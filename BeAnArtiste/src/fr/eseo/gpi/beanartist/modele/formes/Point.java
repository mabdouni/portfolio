package fr.eseo.gpi.beanartist.modele.formes;
import java.text.DecimalFormat;

public class Point {
	private double x,y;
	public Point(){
		this (0.0,0.0);
	}
	public Point(double abs, double ord){
		this.x = abs;
		this.y = ord;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void setX(double abs) {
		this.x = abs;
	}
	public void setY(double ord) {
		this.y = ord;
	}
	public void deplacerVers(double nX, double nY){
		this.x = nX;
		this.y = nY;
	}
	public void deplacerDe(double dX, double dY){
		this.x += dX;
		this.y += dY;
	}
	public String toString(){
		DecimalFormat decimal = new DecimalFormat("0.##");  
		return "(" + decimal.format(getX()) + " , " + decimal.format(getY()) +")";
	}
}
