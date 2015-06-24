package jdt.triangulation;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.Point3D;

public class TriangulationTest {

	private DelaunayTriangulation triangulation;

	@Before
	public void setUp() {
		triangulation = new DelaunayTriangulation();
	}

	@Test
	public void triangulationTest() {
		Point3D pointA = new Point3D(0, 1);
		Point3D pointB = new Point3D(2, 0);
		Point3D pointC = new Point3D(2, 2);
		Point3D pointD = new Point3D(4, 1);
		
		triangulation.insertPoint(pointA);
		triangulation.insertPoint(pointB);
		triangulation.insertPoint(pointC);
		triangulation.insertPoint(pointD);

		List<Triangle> triangles = triangulation.getTriangulation();

		Assert.assertEquals(6, triangles.size());
	}

	@Test
	public void findTest() {

		Point3D pointA = new Point3D(0, 0);
		Point3D pointB = new Point3D(2, 2);
		Point3D pointC = new Point3D(4, 0);

		triangulation.insertPoint(pointA);
		triangulation.insertPoint(pointB);
		triangulation.insertPoint(pointC);

		Point3D pointX = new Point3D(1.3, 1);
		Point3D pointY = new Point3D(4, 2);

		Triangle triangle = triangulation.find(pointX);
		Assert.assertFalse(triangle.isHalfplane());

		Triangle anotherTriangle = triangulation.find(pointY);
		Assert.assertTrue(anotherTriangle.isHalfplane());
	}

}
