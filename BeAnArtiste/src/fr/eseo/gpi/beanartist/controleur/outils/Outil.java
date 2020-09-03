package fr.eseo.gpi.beanartist.controleur.outils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import fr.eseo.gpi.beanartist.modele.formes.Point;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public abstract class Outil implements MouseListener, MouseMotionListener{
	private PanneauDessin panneauDessin;
	private Point Debut;
	private Point Fin;
	
	public Outil(PanneauDessin panneauDessin) {
		associer(panneauDessin);
	}
	public PanneauDessin getPanneauDessin() {
		return panneauDessin; 
	}
	public void setPanneauDessin(PanneauDessin panneauDessin) {
		this.panneauDessin = panneauDessin;
	}
	public void associer(PanneauDessin arg) {
		if (arg !=null && arg.getOutilCourant() !=null)
			arg.getOutilCourant().liberer();
		this.panneauDessin = arg;
		
		if (arg != null) {
			arg.setOutilCourant(this);
			arg.addMouseListener(this);
			arg.addMouseMotionListener(this);
		}
	}
	private void liberer() {
		panneauDessin.removeMouseListener(this);
		panneauDessin.removeMouseMotionListener(this);
		panneauDessin.setOutilCourant(null);
		panneauDessin = null;
	}
	public void mousePressed(MouseEvent event) {
		setDebut(new Point(event.getX(), event.getY()));
	}
	public void mouseReleased (MouseEvent event) {
		setFin((new Point(event.getX(),event.getY())));
	}
	public void mouseClicked (MouseEvent event) {
		
	}
	public void mouseDragged(MouseEvent event) {
		
	}
	public void mouseMoved(MouseEvent event) {
		
	}
	public void mouseEntered(MouseEvent event) {
		
	}
	public void mouseExited(MouseEvent event) {
		
	}
	public void setDebut(Point point) {
		Debut = point;
	}

	public void setFin(Point point) {
		Fin = point;
	}
	public Point getDebut() {
		return this.Debut;
	}

	public Point getFin() {
		return this.Fin;
	}
	
}
