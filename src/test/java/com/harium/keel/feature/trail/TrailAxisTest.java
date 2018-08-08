package com.harium.keel.feature.trail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrailAxisTest {

    private TrailAxis axis;

    @Before
    public void setUp() {
        axis = new TrailAxis(20);
    }

    @Test
    public void testDelta() {

        float firstValue = -2;
        float secondValue = -10;

        Assert.assertEquals(0, axis.getDelta(), 0);

        axis.addValue(firstValue);
        Assert.assertEquals(firstValue, axis.getDelta(), 0);

        axis.addValue(secondValue);
        Assert.assertEquals(secondValue - firstValue, axis.getDelta(), 0);

        Assert.assertTrue(axis.getDeltaMod() > 0);
    }


}
