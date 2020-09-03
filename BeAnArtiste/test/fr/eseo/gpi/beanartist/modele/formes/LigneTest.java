package fr.eseo.gpi.beanartist.modele.formes;

import static org.junit.Assert.*;

import org.junit.Test;

public class LigneTest {
	public static final double EPSILON = 0.1;
	@Test
	public void testConstructeur () {
		Ligne ligne = new Ligne (10,20,15,60);
		assertEquals("abs attendu", 10, ligne.getX() , EPSILON);
		assertEquals("ord attendu", 20, ligne.getY() , EPSILON);
		assertEquals("largeur attendu", 15, ligne.getLargeur() , EPSILON);
		assertEquals("longueur attendu", 60, ligne.getHauteur() , EPSILON); 
	}
	@Test
	public void testperimetre(){
		Ligne ligne = new Ligne (10,10,-5,20);
		assertEquals("perimetre attendu", 20.62, ligne.perimetre() , EPSILON);
	}

}
