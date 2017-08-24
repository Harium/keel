package com.harium.keel;

import java.util.HashSet;
import java.util.Set;

import com.harium.etyl.linear.Point2D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
