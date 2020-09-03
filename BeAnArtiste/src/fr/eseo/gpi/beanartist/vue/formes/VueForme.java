package fr.eseo.gpi.beanartist.vue.formes;

import java.awt.Graphics2D;

import fr.eseo.gpi.beanartist.modele.formes.Forme;

abstract public class VueForme {
	protected final Forme forme;
	public VueForme (Forme forme) {
		this.forme = forme;
	}
	public abstract void affiche(Graphics2D g2D);
	public Forme getForme() {
		return forme;
	}
}
