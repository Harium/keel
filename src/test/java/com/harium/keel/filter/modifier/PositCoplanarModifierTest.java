package com.harium.keel.filter.modifier;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.geometry.Point2D;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.helper.RotationAxis;
import com.harium.keel.modifier.PositCoplanarModifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PositCoplanarModifierTest {

    private static final double EPSILON = 0.1;
    private PositCoplanarModifier posit;

    @Before
    public void setUp() {
        posit = new PositCoplanarModifier(1024, 480);
    }

    @Test
    public void testSimpleImage() {
        List<Point2D> imagePoints = new ArrayList<Point2D>();

        //A,B,C,D (in clockwise order)
        imagePoints.add(new Point2D(0, 0));//A
        imagePoints.add(new Point2D(100, 0));//B
        imagePoints.add(new Point2D(100, 100));//C
        imagePoints.add(new Point2D(0, 100));//D

        PointFeature component = new PointFeature();
        component.setPoints(imagePoints);

        RotationAxis axis = posit.modify(component);

        Matrix4 matrix = axis.getMatrix();

        Quaternion quaternion = new Quaternion();
        quaternion.setFromMatrix(matrix);

        //After Rotation Axis adaptations
        Assert.assertEquals(0, quaternion.getPitch(), EPSILON);
        Assert.assertEquals(0, quaternion.getYaw(), EPSILON);
        Assert.assertEquals(0, quaternion.getRoll(), EPSILON);

        Vector3 position = matrix.getTranslation(new Vector3());

        Assert.assertEquals(-4.6, position.x, EPSILON);
        Assert.assertEquals(1.9, position.y, EPSILON);
        Assert.assertEquals(5.12, position.z, EPSILON);
    }

    @Test
    public void testRotatedImage() {
        List<Point2D> imagePoints = new ArrayList<Point2D>();

        //A square rotated 45 degrees
        //A,B,C,D (in clockwise order)
        imagePoints.add(new Point2D(70, 0));//A
        imagePoints.add(new Point2D(141, 71));//B
        imagePoints.add(new Point2D(71, 141));//C
        imagePoints.add(new Point2D(0, 70));//D

        PointFeature component = new PointFeature();
        component.setPoints(imagePoints);

        RotationAxis axis = posit.modify(component);

        Matrix4 matrix = axis.getMatrix();

        Quaternion quaternion = new Quaternion();
        quaternion.setFromMatrix(matrix);

        //After Rotation Axis adaptations
        Assert.assertEquals(-24.13, quaternion.getPitch(), EPSILON);
        Assert.assertEquals(81.34, quaternion.getYaw(), EPSILON);
        Assert.assertEquals(19.90, quaternion.getRoll(), EPSILON);

        Vector3 position = matrix.getTranslation(new Vector3());

        Assert.assertEquals(3.8, position.x, EPSILON);
        Assert.assertEquals(2.15, position.y, EPSILON);
        Assert.assertEquals(5.57, position.z, EPSILON);
    }

    @Test
    public void testPosit() {
        List<Point2D> imagePoints = new ArrayList<Point2D>();

        //A,B,C,D in clockwise order
        imagePoints.add(new Point2D(447, 223));//A
        imagePoints.add(new Point2D(575, 223));//B
        imagePoints.add(new Point2D(432, 324));//C
        imagePoints.add(new Point2D(590, 324));//D

        PointFeature component = new PointFeature(600, 600);
        component.setPoints(imagePoints);

        RotationAxis axis = posit.modify(component);

        Matrix4 matrix = axis.getMatrix();

        Quaternion quaternion = new Quaternion();
        quaternion.setFromMatrix(matrix);

        //After Rotation Axis adaptations
        Assert.assertEquals(-5.91, quaternion.getPitch(), EPSILON);
        Assert.assertEquals(76.95, quaternion.getYaw(), EPSILON);
        Assert.assertEquals(-31.38, quaternion.getRoll(), EPSILON);

        Vector3 position = matrix.getTranslation(new Vector3());
        Assert.assertEquals(4.31, position.x, EPSILON);
        Assert.assertEquals(0.34, position.y, EPSILON);
        Assert.assertEquals(1.30, position.z, EPSILON);
    }

}
