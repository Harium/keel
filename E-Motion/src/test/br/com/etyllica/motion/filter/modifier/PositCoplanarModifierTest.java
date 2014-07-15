package test.br.com.etyllica.motion.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.helper.RotationAxis;
import br.com.etyllica.motion.modifier.PositCoplanarModifier;

public class PositCoplanarModifierTest {

	private PositCoplanarModifier posit;
	
	@Before
	public void setUp(){
		
		posit = new PositCoplanarModifier(1024,480);
		
	}
	
	@Test
	public void testPosit(){
				
		List<Point2D> imagePoints = new ArrayList<Point2D>();

		imagePoints.add(new Point2D( 447, 223));//A
		imagePoints.add(new Point2D( 575, 223));//B
		imagePoints.add(new Point2D( 590, 324));//D
		imagePoints.add(new Point2D( 432, 324));//C
		
		Component component = new Component(600, 600);
		component.setPoints(imagePoints);
		
		RotationAxis axis = posit.modify(component);

		double tolerance = 0.5;
		
		/*Assert.assertEquals(0, axis.getRotationX(), tolerance);
		Assert.assertEquals(1, axis.getRotationY(), tolerance);
		Assert.assertEquals(0, axis.getRotationZ(), tolerance);
		Assert.assertEquals(90+40.0, axis.getAngle(), 0.01);
		
		Assert.assertEquals(3.6874, axis.getX(), 0.01);
		Assert.assertEquals(1.94869, axis.getY(), 0.01);
		Assert.assertEquals(6.29877, axis.getZ(), 0.01);
		*/

		//After Rotation Axis adaptations
		Assert.assertEquals(0, axis.getRotationX(), tolerance);
		Assert.assertEquals(0, axis.getRotationY(), tolerance);
		Assert.assertEquals(-1, axis.getRotationZ(), tolerance);
		Assert.assertEquals(75.0, axis.getAngle(), 0.5);
				
		Assert.assertEquals(-0.2368, axis.getX(), 0.01);
		Assert.assertEquals(-0.3194, axis.getY(), 0.01);
		Assert.assertEquals(+9.0877, axis.getZ(), 0.01);
		
	}
	
}
