package test.br.com.etyllica.motion.filter.modifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opencv.OpenCv;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.modifier.PositModifier;

public class PositModiferTest {

	private PositModifier modifier;
	
	@Before
	public void setUp(){
		
		double focalLength = 760;
		
		modifier = new PositModifier(focalLength, 0);
		
	}
		
	
	public void testModifier(){
		
		Component imageComponent = new BoundingComponent(200, 200);
		
		imageComponent.add(new Point2D(0,0));
		
		modifier.modifyComponent(imageComponent);

		double axisX = modifier.getAxisX();

		double axisY = modifier.getAxisY();

		double axisZ = modifier.getAxisZ();

		double length = Math.sqrt(OpenCv.cvSqr(axisX)+OpenCv.cvSqr(axisY)+OpenCv.cvSqr(axisZ));

		Assert.assertEquals(1, length, 0.001);

		System.out.println("Rotation Angle: " + modifier.getAngle());
		
		System.out.println("Rotation X Axis: "+ axisX);
		
		System.out.println("Rotation Y Axis: "+ axisY);
		
		System.out.println("Rotation Z Axis: "+ axisZ);

	}

	@Test
	public void testComputeRotationValues(){

		double[] rotation = {0.36, 0.48, 0.38, -0.8, 0.6, 0, 0.48, 0.64, 0.60};

		modifier.computeRotationValues(rotation);
		
		double angle = Math.toRadians(modifier.getAngle());

		double axisX = modifier.getAxisX();

		double axisY = modifier.getAxisY();

		double axisZ = modifier.getAxisZ();

		Assert.assertEquals(1.2870022175865687, angle, 0.05);

		Assert.assertEquals(0.4461257476336151, -axisX, 0.05);

		Assert.assertEquals(-0.06970714806775234, -axisY, 0.05);

		Assert.assertEquals(-0.8922514952672302, -axisZ, 0.05);

		double length = Math.sqrt(OpenCv.cvSqr(axisX)+OpenCv.cvSqr(axisY)+OpenCv.cvSqr(axisZ));

		Assert.assertEquals(1, length, 0.001);

	}

}
