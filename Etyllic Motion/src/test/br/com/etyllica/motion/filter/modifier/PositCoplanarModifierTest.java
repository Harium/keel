package test.br.com.etyllica.motion.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.modifier.PositCoplanarModifier;

public class PositCoplanarModifierTest {

	private PositCoplanarModifier posit;
	
	@Before
	public void setUp(){
		
		posit = new PositCoplanarModifier();
		
	}
	
	@Test
	public void testPosit(){
				
		List<Point2D> imagePoints = new ArrayList<Point2D>();

		imagePoints.add(new Point2D( 447, 223));//A
		imagePoints.add(new Point2D( 575, 223));//B
		imagePoints.add(new Point2D( 590, 324));//D
		imagePoints.add(new Point2D( 432, 324));//C
		
		Component component = new BoundingComponent(600, 600);
		component.setPoints(imagePoints);
		
		posit.modifyComponent(component);

		double tolerance = 0.5;
		
		Assert.assertEquals(1, posit.getAxisX(), tolerance);
		Assert.assertEquals(0, posit.getAxisY(), tolerance);
		Assert.assertEquals(0, posit.getAxisZ(), tolerance);
		
		Assert.assertEquals(90+40.0, posit.getAngle(), 0.01);
		
		double[] expectedTranslation = {3.6874, 1.94869, 6.29877};
		
		Assert.assertArrayEquals(expectedTranslation, posit.getTranslation(), 0.01);
		
	}
	
}
