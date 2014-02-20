package test.org.opencv.modules.calib3d;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opencv.OpenCv;
import org.opencv.criteria.CvTermCriteria;
import org.opencv.modules.calib3d.Posit;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.Point3D;

public class PositTest {

	private Posit posit;
	
	@Before
	public void setUp(){
		
		posit = new Posit();
		
	}
	
	@Test
	public void testPosit(){
		
		double focalLength = 760;
		
		//Projecting from (0,0,0) to right 
		Point3D[] objPoints = {new Point3D(0,0,0), new Point3D(10,0,0), new Point3D(10,10,0), new Point3D(0,10,0), new Point3D(0,0,10), new Point3D(10,0,10), new Point3D(10,10,10), new Point3D(0,10,10)}; 
		
		Point2D[] imagePoints = {new Point2D(0,0), new Point2D(80,-93), new Point2D(245,-77), new Point2D(185,32), new Point2D(32,135), new Point2D(99,35), new Point2D(247,62), new Point2D(195,179)};
		
		posit.icvPOSIT(objPoints, imagePoints, focalLength, new CvTermCriteria(30));
		
		double[] expectedRotation = {0.4901052331209617, 0.8505635867007797, 0.1906264552630432, -0.5694840923010308, 0.1467075207288464, 0.8088045326144422, 0.6599733495634182, -0.5049580678503289, 0.5562845557640123};
		
		Assert.assertArrayEquals(expectedRotation, posit.getRotation(), 0.01);
		
		double[] expectedTranslation = {0, 0, 40.02681933541206};
		
		Assert.assertArrayEquals(expectedTranslation, posit.getTranslation(), 0.01);
		
		System.out.println(posit.getAngle());
		
		System.out.println(posit.getAxisX());
		System.out.println(posit.getAxisY());
		System.out.println(posit.getAxisZ());
						
	}
	
	@Test
	public void testComputeRotationValues(){
		
		double[] rotation = {0.36, 0.48, 0.38, -0.8, 0.6, 0, 0.48, 0.64, 0.60};
		
		//double angle = Math.toDegrees(Math.acos(( rotation[0+3*0] + rotation[1+3*1] + rotation[2+3*2] - 1)/2));
		double angle = Math.acos(( rotation[0+3*0] + rotation[1+3*1] + rotation[2+3*2] - 1)/2);
		
		double norm = Math.sqrt(OpenCv.cvSqr(rotation[2+3*1] - rotation[1+3*2])+OpenCv.cvSqr(rotation[0+3*2] - rotation[2+3*0])+OpenCv.cvSqr(rotation[1+3*0] - rotation[0+3*1]));
		
		double axisX = (rotation[2+3*1] - rotation[1+3*2])/norm;

		double axisY = (rotation[0+3*2] - rotation[2+3*0])/norm;

		double axisZ = (rotation[1+3*0] - rotation[0+3*1])/norm;
		
		Assert.assertEquals(1.2870022175865687, angle, 0.05);
		
		Assert.assertEquals(0.4461257476336151, -axisX, 0.05);
				
		Assert.assertEquals(-0.06970714806775234, -axisY, 0.05);
		
		Assert.assertEquals(-0.8922514952672302, -axisZ, 0.05);
		
		double length = Math.sqrt(OpenCv.cvSqr(axisX)+OpenCv.cvSqr(axisY)+OpenCv.cvSqr(axisZ));
		
		Assert.assertEquals(1, length, 0.001);
		
	}
	
}
