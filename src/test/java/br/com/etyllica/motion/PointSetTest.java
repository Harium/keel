package br.com.etyllica.motion;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.Point2D;

public class PointSetTest {

	private Set<Point2D> set = new HashSet<Point2D>();
	
	@Before
	public void setUp() {
		set.clear();
	}
	
	@Test
	public void addOnePointTwoTimes() {
		
		set.add(new Point2D(20, 20));
		set.add(new Point2D(20, 20));
		
		Assert.assertEquals(1, set.size());
		
	}
	
	@Test
	public void addTwoPoints() {
		
		set.add(new Point2D(20, 20));
		set.add(new Point2D(21, 20));
		
		Assert.assertEquals(2, set.size());
		
	}
	
}
