package fr.eseo.gpi.beanartist.modele.formes;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarreTest {
	private static final double EPSILON = 1e-7d;
	@Test
	public void testConstructeur() {
		Carre carre = new Carre ( 1 , 2 , 10 );
		assertEquals("abs attendu", 1, carre.getX() , EPSILON);
		assertEquals("ord attendu", 2, carre.getY() , EPSILON);
		assertEquals("largeur attendu", 10, carre.getLargeur() , EPSILON);
		assertEquals("longueur attendu", 10, carre.getHauteur() , EPSILON);
	}
	@Test 
	public void testsetHauteur() {
		Carre carre = new Carre( 1 , 2 , 10);
		carre.setHauteur(12);
		assertEquals("hauteur attendu", 12, carre.getHauteur() , EPSILON);
		try{
			carre.setHauteur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("hauteur nÃ©gative"));
		 }
	}
	@Test
	public void testsetLargeur() {
		Carre carre = new Carre( 1 , 2 , 10);
		carre.setLargeur(12);
		assertEquals("hauteur attendu", 12, carre.getLargeur() , EPSILON);
		try{
			carre.setLargeur(-12);
			fail("Should throw exception when setting a negative number");
		}
		catch(IllegalArgumentException aExp){
		    assert(aExp.getMessage().contains("largeur nÃ©gative"));
		 }
	}
	@Test
	public void testaire() {
		Carre carre = new Carre( 1 , 2 , 10);
		assertEquals("aire attendu", 100 , carre.aire() , EPSILON);
	}
	@Test
	public void testperimetre() {
		Carre carre = new Carre( 1 , 2 , 25);
		assertEquals("perimetre attendu", 100 , carre.perimetre() , EPSILON);
	}
	@Test
	public void testtoString() {
		Carre carre = new Carre( 1 , 2 , 10);
		assertEquals("[Carre] pos (1 , 2) dim 10 x 10 périmètre : 40 aire : 100", carre.toString());
	}

}
