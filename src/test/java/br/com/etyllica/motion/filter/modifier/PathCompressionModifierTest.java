package br.com.etyllica.motion.filter.modifier;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.modifier.smooth.PathCompressionModifier;

public class PathCompressionModifierTest {

	private PathCompressionModifier modifier;
	
	private HullComponent heavyTriangle;
	
	private HullComponent heavyLine;
	
	@Before
	public void setUp() {
		modifier = new PathCompressionModifier();
		
		heavyLine = new HullComponent();
		heavyLine.addLogic(new Point2D(10,0));
		heavyLine.addLogic(new Point2D(5,0));
		heavyLine.addLogic(new Point2D(0,0));
		
		heavyTriangle = new HullComponent();
		heavyTriangle.addLogic(new Point2D(10,0));
		heavyTriangle.addLogic(new Point2D(5,0));
		heavyTriangle.addLogic(new Point2D(0,0));
		heavyTriangle.addLogic(new Point2D(0,5));
		heavyTriangle.addLogic(new Point2D(0,10));
		heavyTriangle.addLogic(new Point2D(10,0)); //Closed Triangle
	}
	
	@Test
	public void testModifyLine() {
		List<Point2D> points = modifier.modify(heavyLine);
		Assert.assertEquals(2, points.size());
	}
	
	@Test
	public void testModifyClosedTriangle() {
		List<Point2D> points = modifier.modify(heavyTriangle);
		
		Assert.assertEquals(4, points.size());
		Assert.assertEquals(new Point2D(10,0), points.get(0));
		Assert.assertEquals(new Point2D(0,0), points.get(1));
		Assert.assertEquals(new Point2D(0,10), points.get(2));
		Assert.assertEquals(new Point2D(10,0), points.get(3));
	}
	
}
