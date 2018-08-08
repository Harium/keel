package jdt.triangulation;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TriangulationTest {

    private DelaunayTriangulation triangulation;

    @Before
    public void setUp() {
        triangulation = new DelaunayTriangulation();
    }

    @Test
    public void triangulationTest() {
        Vector3 pointA = new Vector3(0, 1, 0);
        Vector3 pointB = new Vector3(2, 0, 0);
        Vector3 pointC = new Vector3(2, 2, 0);
        Vector3 pointD = new Vector3(4, 1, 0);

        List<Vector3> pointCloud = new ArrayList<Vector3>();
        pointCloud.add(pointA);
        pointCloud.add(pointB);
        pointCloud.add(pointC);
        pointCloud.add(pointD);

        List<Triangle> triangles = triangulation.triangulate(pointCloud);

        Assert.assertEquals(2, triangles.size());
    }

    @Test
    public void findTest() {
        Vector3 pointA = new Vector3(0, 0, 0);
        Vector3 pointB = new Vector3(2, 2, 0);
        Vector3 pointC = new Vector3(4, 0, 0);

        List<Vector3> pointCloud = new ArrayList<Vector3>();
        pointCloud.add(pointA);
        pointCloud.add(pointB);
        pointCloud.add(pointC);

        triangulation.triangulate(pointCloud);

        Vector3 pointX = new Vector3(1.3f, 1, 0);
        Vector3 pointY = new Vector3(4, 2, 0);

        Triangle triangle = triangulation.find(pointX);
        Assert.assertFalse(triangle.isHalfplane());

        Triangle anotherTriangle = triangulation.find(pointY);
        Assert.assertTrue(anotherTriangle.isHalfplane());
    }

    @Test
    public void findConnectedVerticesTest() {

        Vector3 pointA = new Vector3(0, 0, 0);
        Vector3 pointB = new Vector3(2, 2, 0);
        Vector3 pointC = new Vector3(4, 0, 0);

        List<Vector3> pointCloud = new ArrayList<Vector3>();
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
