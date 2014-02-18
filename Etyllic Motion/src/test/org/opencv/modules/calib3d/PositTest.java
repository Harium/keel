package test.org.opencv.modules.calib3d;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opencv.modules.calib3d.Posit;

public class PositTest {

	private Posit posit;
	
	@Before
	public void setUp(){
		
		posit = new Posit();
		
	}
	
	@Test
	public void testPseudoInverse(){

		double[] a = 
			{-1, 9, 2, 8, 7, 
		 	 5, 6, -5, 7, 2, 
			-9, 0, 1, 2, -3};
				
		double[] inv = posit.icvPseudoInverse3D(a, 5, 0);
		
		double[] b = {
				
				-0.01449, 0.03614, 0.06480,
				
				0.02191, 0.01631, 0.00493, 
				
				-0.08413, -0.09792, -0.03686,
				0.00593, 0.05997, 0.04719, 
				0.06903, -0.05840, -0.06176};
		
		Assert.assertArrayEquals(b, inv, 0.1);
		
		
	}
	
}
