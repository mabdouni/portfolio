package fr.eseo.gpi.beanartist.modele.formes;
import static org.junit.Assert.*;

import org.junit.Test;

import fr.eseo.gpi.beanartist.modele.formes.Point;


public class PointTest {
	private static final double EPSILON = 1e-7d;
	@Test 
	public void testConstructeur() {
		Point point = new Point();
		assertEquals("X attendu", 0.0, point.getX(), EPSILON);
		assertEquals("Y attendu", 0.0, point.getY(), EPSILON);
	}
	@Test
	public void testConstructeur2() {
		Point point = new Point(5,6);
		assertEquals("X attendu", 5, point.getX(), EPSILON);
		assertEquals("Y attendu", 6, point.getY(), EPSILON);
	}
	@Test
	public void testDeplacerVers() {
		Point point = new Point(5,6);
		point.deplacerVers(15, 12);
		assertEquals("X attendu", 15, point.getX(), EPSILON);
		assertEquals("Y attendu", 12, point.getY(), EPSILON);
	}
	@Test
	public void testDeplacerDe() {
		Point point = new Point(5,6);
		point.deplacerDe(5, 4);
		assertEquals("X attendu", 10, point.getX(), EPSILON);
		assertEquals("Y attendu", 10, point.getY(), EPSILON);
	}
	@Test
	public void testtoString() {
		Point point = new Point(5.12,6.14);
		assertEquals("chaine attendu", "(5,12 , 6,14)", point.toString());
	}
}