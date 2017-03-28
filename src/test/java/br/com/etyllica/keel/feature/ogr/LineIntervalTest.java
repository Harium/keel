package br.com.etyllica.keel.feature.ogr;

import org.junit.Assert;
import org.junit.Test;

import br.com.etyllica.keel.feature.ogr.LineInterval;

public class LineIntervalTest {

	@Test
	public void creationTest() {
		LineInterval interval = new LineInterval(0, 5, 0);
		
		Assert.assertEquals(0, interval.getStart());
		Assert.assertEquals(5, interval.getLength());
	}
	
	@Test
	public void overlapIntersectionTest() {
		LineInterval interval = new LineInterval(0, 10, 0);
		LineInterval anotherinterval = new LineInterval(5, 5, 0);
		
		Assert.assertTrue(interval.intersect(anotherinterval));
	}
	
	@Test
	public void intersectionTest() {
		LineInterval interval = new LineInterval(0, 10, 0);
		LineInterval anotherinterval = new LineInterval(5, 5, 1);
		
		Assert.assertTrue(interval.intersect(anotherinterval));
	}
	
	@Test
	public void diagonalIntersectionTest() {
		LineInterval interval = new LineInterval(0, 10, 0);
		LineInterval anotherinterval = new LineInterval(10, 5, 1);
		
		Assert.assertFalse(interval.intersect(anotherinterval));
	}
	
	@Test
	public void notIntersectionTest() {
		LineInterval interval = new LineInterval(0, 10, 0);
		LineInterval anotherinterval = new LineInterval(5, 5, 2);
		
		Assert.assertFalse(interval.intersect(anotherinterval));
	}
}
