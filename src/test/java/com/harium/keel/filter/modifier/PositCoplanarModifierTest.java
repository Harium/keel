package com.harium.keel.filter.modifier;

import com.harium.keel.feature.Component;
import com.harium.keel.helper.RotationAxis;
import com.harium.keel.modifier.PositCoplanarModifier;
import com.harium.etyl.linear.Point2D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PositCoplanarModifierTest {

    private PositCoplanarModifier posit;

    @Before
    public void setUp() {

        posit = new PositCoplanarModifier(1024, 480);

    }

    @Test
    public void testPosit() {
        List<Point2D> imagePoints = new ArrayList<Point2D>();

        //A,B,C,D in clockwise order
        imagePoints.add(new Point2D(447, 223));//A
        imagePoints.add(new Point2D(575, 223));//B
        imagePoints.add(new Point2D(432, 324));//C
        imagePoints.add(new Point2D(590, 324));//D

        Component component = new Component(600, 600);
        component.setPoints(imagePoints);

        RotationAxis axis = posit.modify(component);

        double tolerance = 0.59;

		/*Assert.assertEquals(0, axis.getRotationX(), tolerance);
		Assert.assertEquals(1, axis.getRotationY(), tolerance);
		Assert.assertEquals(0, axis.getRotationZ(), tolerance);
		Assert.assertEquals(90+40.0, axis.getAngle(), 0.01);
		
		Assert.assertEquals(3.6874, axis.getX(), 0.01);
		Assert.assertEquals(1.94869, axis.getY(), 0.01);
		Assert.assertEquals(6.29877, axis.getZ(), 0.01);
		*/

        //After Rotation Axis adaptations
        Assert.assertEquals(-0.9, axis.getRotationX(), tolerance);
        Assert.assertEquals(0, axis.getRotationY(), tolerance);
        Assert.assertEquals(0.03, axis.getRotationZ(), tolerance);
        Assert.assertEquals(48.0, axis.getAngle(), tolerance);

        Assert.assertEquals(0, axis.getX(), 0.011);
        Assert.assertEquals(4.02, axis.getY(), 0.011);
        Assert.assertEquals(-0.2, axis.getZ(), 0.011);

    }

}
