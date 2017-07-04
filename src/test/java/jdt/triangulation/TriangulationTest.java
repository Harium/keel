package jdt.triangulation;

import java.util.ArrayList;
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
		
		List<Point3D> pointCloud = new ArrayList<Point3D>();
		pointCloud.add(pointA);
		pointCloud.add(pointB);
		pointCloud.add(pointC);
		pointCloud.add(pointD);
		
		List<Triangle> triangles = triangulation.triangulate(pointCloud);
		
		Assert.assertEquals(2, triangles.size());
	}

	@Test
	public void findTest() {
		Point3D pointA = new Point3D(0, 0);
		Point3D pointB = new Point3D(2, 2);
		Point3D pointC = new Point3D(4, 0);

		List<Point3D> pointCloud = new ArrayList<Point3D>();
		pointCloud.add(pointA);
		pointCloud.add(pointB);
		pointCloud.add(pointC);
		
		triangulation.triangulate(pointCloud);

		Point3D pointX = new Point3D(1.3, 1);
		Point3D pointY = new Point3D(4, 2);

		Triangle triangle = triangulation.find(pointX);
		Assert.assertFalse(triangle.isHalfplane());

		Triangle anotherTriangle = triangulation.find(pointY);
		Assert.assertTrue(anotherTriangle.isHalfplane());
	}
		
	@Test
	public void findConnectedVerticesTest() {

		Point3D pointA = new Point3D(0, 0);
		Point3D pointB = new Point3D(2, 2);
		Point3D pointC = new Point3D(4, 0);
		
		List<Point3D> pointCloud = new ArrayList<Point3D>();
		pointCloud.add(pointA);
		pointCloud.add(pointB);
		pointCloud.add(pointC);
		
		List<Triangle> triangles = triangulation.triangulate(pointCloud);
		
		Assert.assertEquals(1, triangles.size());

		/*List<Point3D> connected = triangulation.findConnectedVertices(pointA, triangles);
		
		Assert.assertNotNull(connected);
		Assert.assertEquals(2, connected.size());*/
	}

}
