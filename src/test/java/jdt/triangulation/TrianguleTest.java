package jdt.triangulation;

import org.junit.Assert;
import org.junit.Test;

import br.com.etyllica.core.linear.Point3D;

public class TrianguleTest {

	@Test
	public void testTriangleEquals() {
		
		Point3D pointA = new Point3D(0, 1);
		Point3D pointB = new Point3D(2, 0);
		Point3D pointC = new Point3D(2, 2);
		
		Triangle a = new Triangle(pointA, pointB, pointC);
		Triangle b = new Triangle(pointB, pointC, pointA);
		
		//A.a = B.c
		//A.b = B.a
		//A.c = B.b
				
		Assert.assertTrue(a.equals(b));
	}
	
	@Test
	public void testTriangleByTwoPoints() {
		
		Point3D pointA = new Point3D(0, 1);
		Point3D pointB = new Point3D(2, 0);
		
		Triangle a = new Triangle(pointA, pointB);
		Assert.assertTrue(a.isHalfplane());
		
		//A.a = B.c
		//A.b = B.a
		//A.c = B.b
				
		Assert.assertNotNull(a.c);
		Assert.assertEquals(0.13, a.c.getX(), 0.1);
		Assert.assertEquals(-1.23, a.c.getY(), 0.1);
	}

}
