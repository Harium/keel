package jdt.triangulation;

import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

public class TriangleTest {

    @Test
    public void testTriangleEquals() {

        Vector3 pointA = new Vector3(0, 1, 0);
        Vector3 pointB = new Vector3(2, 0, 0);
        Vector3 pointC = new Vector3(2, 2, 0);

        Triangle a = new Triangle(pointA, pointB, pointC);
        Triangle b = new Triangle(pointB, pointC, pointA);

        //A.a = B.c
        //A.b = B.a
        //A.c = B.b

        Assert.assertTrue(a.equals(b));
    }

    @Test
    public void testTriangleByTwoPoints() {
        Vector3 pointA = new Vector3(0, 1, 0);
        Vector3 pointB = new Vector3(2, 0, 0);

        Triangle a = new Triangle(pointA, pointB);
        Assert.assertTrue(a.isHalfplane());

        //A.a = B.c
        //A.b = B.a
        //A.c = B.b

        Assert.assertNotNull(a.c);
        Assert.assertEquals(0.13, a.c.x, 0.1);
        Assert.assertEquals(-1.23, a.c.y, 0.1);
    }

}
