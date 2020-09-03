package fr.eseo.gpi.beanartist.modele.formes;

import static org.junit.Assert.*;

import org.junit.Test;

public class RectangleTest {
	private static final double EPSILON = 1e-7d;
	@Test
	public void testConstructeur() {
		Rectangle rectangle = new Rectangle( 1 , 2 , 10 , 12);
		assertEquals("abs attendu", 1, rectangle.getX() , EPSILON);
		assertEquals("ord attendu", 2, rectangle.getY() , EPSILON);
		assertEquals("largeur attendu", 10, rectangle.getLargeur() , EPSILON);
		assertEquals("longueur attendu", 12, rectangle.getHauteur() , EPSILON);
	}
	@Test 
	public void testsetHauteur() {
		Rectangle rectangle = new Rectangle( 1 , 2 , 10 , 10);
		rectangle.setHauteur(12);
		assertEquals("hauteur attendu", 12, rectangle.getHauteur() , EPSILON);
		try{
			rectangle.setHauteur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("hauteur négative"));
		 }
	}
	@Test
	public void testsetLargeur() {
		Rectangle rectangle = new Rectangle( 1 , 2 , 10 , 10);
		rectangle.setLargeur(12);
		assertEquals("hauteur attendu", 12, rectangle.getLargeur() , EPSILON);
		try{
			rectangle.setLargeur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("largeur négative"));
		 }
	}
	@Test
	public void testaire() {
		Rectangle rectangle = new Rectangle( 1 , 2 , 25 , 15);
		assertEquals("aire attendu", 375 , rectangle.aire() , EPSILON);
	}
	@Test
	public void testperimetre() {
		Rectangle rectangle = new Rectangle( 1 , 2 , 25 , 15);
		assertEquals("perimetre attendu", 80 , rectangle.perimetre() , EPSILON);
	}
	@Test
	public void testtoString() {
		Rectangle rectangle = new Rectangle( 10 , 10 , 25 , 15);
		assertEquals("[Rectangle] pos (10 , 10) dim 25 x 15 périmètre : 80 aire : 375", rectangle.toString());
	}
}
