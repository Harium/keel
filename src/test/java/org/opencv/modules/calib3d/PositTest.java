package org.opencv.modules.calib3d;

import com.harium.etyl.linear.Point2D;
import com.harium.etyl.linear.Point3D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opencv.criteria.CvTermCriteria;

import java.util.ArrayList;
import java.util.List;


public class PositTest {

    private Posit posit;

    @Before
    public void setUp() {
        posit = new Posit();
    }

    @Test
    public void testPosit() {

        double focalLength = 760;

        //Projecting from (0,0,0) to right
        List<Point3D> objPoints = new ArrayList<Point3D>();

        objPoints.add(new Point3D(0, 0, 0));
        objPoints.add(new Point3D(10, 0, 0));
        objPoints.add(new Point3D(10, 10, 0));
        objPoints.add(new Point3D(0, 10, 0));
        objPoints.add(new Point3D(0, 0, 10));
        objPoints.add(new Point3D(10, 0, 10));
        objPoints.add(new Point3D(10, 10, 10));
        objPoints.add(new Point3D(0, 10, 10));

        List<Point2D> imagePoints = new ArrayList<Point2D>();

        imagePoints.add(new Point2D(0, 0));
        imagePoints.add(new Point2D(80, -93));
        imagePoints.add(new Point2D(245, -77));
        imagePoints.add(new Point2D(185, 32));
        imagePoints.add(new Point2D(32, 135));
        imagePoints.add(new Point2D(99, 35));
        imagePoints.add(new Point2D(247, 62));
        imagePoints.add(new Point2D(195, 179));

        posit.icvPOSIT(objPoints, imagePoints, focalLength, new CvTermCriteria(30));

        double[] expectedRotation = {0.4901052331209617, 0.8505635867007797, 0.1906264552630432, -0.5694840923010308, 0.1467075207288464, 0.8088045326144422, 0.6599733495634182, -0.5049580678503289, 0.5562845557640123};

        Assert.assertArrayEquals(expectedRotation, posit.getRotation(), 0.01);

        double[] expectedTranslation = {0, 0, 40.02681933541206};

        Assert.assertArrayEquals(expectedTranslation, posit.getTranslation(), 0.01);
    }

}
