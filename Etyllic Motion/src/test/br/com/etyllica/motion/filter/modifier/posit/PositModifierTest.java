package test.br.com.etyllica.motion.filter.modifier.posit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.Point3D;
import br.com.etyllica.motion.filter.modifier.posit.PositModifier;
import br.com.etyllica.motion.filter.modifier.posit.SVD;
import br.com.etyllica.motion.filter.modifier.posit.TImage;
import br.com.etyllica.motion.filter.modifier.posit.TObject;

public class PositModifierTest {

	private PositModifier modifier;
	
	private TObject object;
	
	private TImage image;
	
	@Before
	public void setUp(){
				
		List<Point3D> cubePoints = new ArrayList<Point3D>();
		
		cubePoints.add(new Point3D(0,0,0));
		cubePoints.add(new Point3D(10,0,0));
		cubePoints.add(new Point3D(10,10,0));
		cubePoints.add(new Point3D(0,10,0));
		cubePoints.add(new Point3D(0,0,10));
		cubePoints.add(new Point3D(10,0,10));
		cubePoints.add(new Point3D(10,10,10));
		cubePoints.add(new Point3D(0,10,10));
		
		object = new TObject(cubePoints);
		
		List<Point2D> imagePoints = new ArrayList<Point2D>();
		
		imagePoints.add(new Point2D(0,0));
		imagePoints.add(new Point2D(80,-93));
		imagePoints.add(new Point2D(245,-77));
		imagePoints.add(new Point2D(185,32));
		imagePoints.add(new Point2D(32,135));
		imagePoints.add(new Point2D(99,35));
		imagePoints.add(new Point2D(247,62));
		imagePoints.add(new Point2D(195,179));
		
		image = new TImage(imagePoints);
		
		modifier = new PositModifier();
		
		modifier.focalLength = 760;
				
	}
	
	@Test
	public void testModifier(){
		
		//testObject.objectMatrix
				
		modifier.POSIT(object, image);

		//Print results 
		System.out.println("Rotation matrix of scene in camera reference frame\n");
		PrintRotation(modifier.getRotation());
		
		System.out.println("Translation vector of scene in camera reference frame\n"
				+ "from camera projecction center to FIRST point of scene (object) file\n");
		
		//PrintVector(modifier.getTranslation(), 3);
		
	}
	
	private void PrintRotation(double rotation[][]){
	int i, j;

		for (i=0; i<3;i++){
			for (j=0; j<3; j++){
				System.out.printf("%.3f ", rotation[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
	
}
