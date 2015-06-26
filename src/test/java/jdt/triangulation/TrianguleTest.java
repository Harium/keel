package jdt.triangulation;

import org.junit.Assert;
import org.junit.Test;

import br.com.etyllica.linear.Point3D;

public class TrianguleTest {

	@Test
	public void testTriangleEquals() {
		
		Point3D pointA = new Point3D(0, 1);
		Point3D pointB = new Point3D(2, 0);
		Point3D pointC = new Point3D(2, 2);
		
		Triangle a = new Triangle(pointA, pointB, pointC);
		Triangle b = new Triangle(pointA, pointC, pointB);
		
		Assert.assertTrue(a.equals(b));
	}

}
