package com.harium.keel.filter.modifier;

import com.badlogic.gdx.math.Quaternion;
import com.harium.etyl.geometry.Point2D;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.modifier.PositModifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PositModiferTest {

    private static final double EPSILON = 0.01;
    private PositModifier modifier;

    @Before
    public void setUp() {
        double focalLength = 100;
        modifier = new PositModifier(focalLength, 0);
    }

    //Ignore Test
    //@Test
    public void testModifier() {
        PointFeature imageComponent = new PointFeature(200, 200);

        imageComponent.add(new Point2D(0, 0));
        imageComponent.add(new Point2D(100, 0));
        imageComponent.add(new Point2D(100, 100));
        imageComponent.add(new Point2D(0, 100));

        modifier.modifyComponent(imageComponent);
        Quaternion quaternion = modifier.calculateQuaternion();

        Assert.assertEquals(1, quaternion.getPitch(), EPSILON);
        Assert.assertEquals(1, quaternion.getYaw(), EPSILON);
        Assert.assertEquals(1, quaternion.getRoll(), EPSILON);
    }

}
