package fr.eseo.gpi.beanartist.modele.formes;

import static org.junit.Assert.*;

import org.junit.Test;

public class CercleTest {

	private static final double EPSILON = 1e-7d;
	@Test
	public void testConstructeur() {
		Cercle cercle = new Cercle ( 1 , 2 , 10 );
		assertEquals("abs attendu", 1, cercle.getX() , EPSILON);
		assertEquals("ord attendu", 2, cercle.getY() , EPSILON);
		assertEquals("largeur attendu", 10, cercle.getLargeur() , EPSILON);
		assertEquals("longueur attendu", 10, cercle.getHauteur() , EPSILON);
	}
	@Test 
	public void testsetHauteur() {
		Cercle cercle = new Cercle( 1 , 2 , 10);
		cercle.setHauteur(12);
		assertEquals("hauteur attendu", 12, cercle.getHauteur() , EPSILON);
		try{
			cercle.setHauteur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("hauteur négative"));
		 }
	}
	@Test
	public void testsetLargeur() {
		Cercle cercle = new Cercle( 1 , 2 , 10);
		cercle.setLargeur(12);
		assertEquals("hauteur attendu", 12, cercle.getLargeur() , EPSILON);
		try{
			cercle.setLargeur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("largeur négative"));
		 }
	}
	@Test
	public void testaire() {
		Cercle cercle = new Cercle( 1 , 2 , 10);
		assertEquals("aire attendu", 78.54 , (double) Math.round(cercle.aire() * 100) / 100 , EPSILON);
	}
	@Test
	public void testperimetre() {
		Cercle cercle = new Cercle( 1 , 2 , 10);
	assertEquals("perimetre attendu", 31.42 , (double) Math.round(cercle.perimetre() * 100) / 100 , EPSILON);
	}
	@Test
	public void testtoString() {
		Cercle cercle = new Cercle( 1 , 2 , 10);
		assertEquals("[Cercle] pos (1 , 2) dim 10 x 10 périmètre : 31,42 aire : 78,54", cercle.toString());
	}
}
