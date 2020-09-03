package fr.eseo.gpi.beanartist.modele.formes;

import static org.junit.Assert.*;

import org.junit.Test;

public class EllipseTest {

	private static final double EPSILON = 1e-7d;
	@Test
	public void testConstructeur() {
		Ellipse ellipse = new Ellipse( 1 , 2 , 10 , 12);
		assertEquals("abs attendu", 1, ellipse.getX() , EPSILON);
		assertEquals("ord attendu", 2, ellipse.getY() , EPSILON);
		assertEquals("largeur attendu", 10, ellipse.getLargeur() , EPSILON);
		assertEquals("longueur attendu", 12, ellipse.getHauteur() , EPSILON);
	}
	@Test 
	public void testsetHauteur() {
		Ellipse ellipse = new Ellipse( 1 , 2 , 10 , 10);
		ellipse.setHauteur(12);
		assertEquals("hauteur attendu", 12, ellipse.getHauteur() , EPSILON);
		try{
			ellipse.setHauteur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("hauteur négative"));
		 }
	}
	@Test
	public void testsetLargeur() {
		Ellipse ellipse = new Ellipse( 1 , 2 , 10 , 10);
		ellipse.setLargeur(12);
		assertEquals("hauteur attendu", 12, ellipse.getLargeur() , EPSILON);
		try{
			ellipse.setLargeur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("largeur négative"));
		 }
	}
	@Test
	public void testaire() {
		Ellipse ellipse = new Ellipse( 1 , 2 , 25 , 15);
		assertEquals("aire attendu", 294.52 , ellipse.aire() , EPSILON);
	}
	@Test
	public void testperimetre() {
		Ellipse ellipse = new Ellipse( 1 , 2 , 25 , 15);
		assertEquals("perimetre attendu", 63.82 , ellipse.perimetre() , EPSILON);
	}
	@Test
	public void testtoString() {
		Ellipse ellipse = new Ellipse( 10 , 10 , 25 , 15);
		assertEquals("[Ellipse] pos (10 , 10) dim 25 x 15 périmètre : 63,82 aire : 294,52", ellipse.toString());
	}
}
