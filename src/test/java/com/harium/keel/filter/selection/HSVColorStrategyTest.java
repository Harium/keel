package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Test;

public class HSVColorStrategyTest {

    private static final float EPSILON = 0.1f;

    @Test
    public void testConstructorMaxLowerThenMin() {
        float tolerance = 0.2f;

        HSVColorStrategy strategy = new HSVColorStrategy(Color.RED.getRGB(), tolerance);
        Assert.assertEquals(0.8f, strategy.minH, EPSILON);
        Assert.assertEquals(0.2f, strategy.maxH, EPSILON);// maxH < minH is expected since hue is circular
        Assert.assertEquals(0.8f, strategy.minS, EPSILON);
        Assert.assertEquals(1.0f, strategy.maxS, EPSILON);
        Assert.assertEquals(0.8f, strategy.minV, EPSILON);
        Assert.assertEquals(1.0f, strategy.maxV, EPSILON);
    }

    @Test
    public void testConstructorWithIndividualTolerances() {
        float sTolerance = 0.2f;
        float lTolerance = 0.3f;

        HSVColorStrategy strategy = new HSVColorStrategy(Color.BLUE.getRGB(), 0.001f, sTolerance, lTolerance);
        Assert.assertEquals(239.999f, strategy.minH, EPSILON);
        Assert.assertEquals(239.001f, strategy.maxH, EPSILON);// This is expected since hue is circular
        Assert.assertEquals(0.8f, strategy.minS, EPSILON);
        Assert.assertEquals(1.0f, strategy.maxS, EPSILON);
        Assert.assertEquals(0.7f, strategy.minV, EPSILON);
        Assert.assertEquals(1.0f, strategy.maxV, EPSILON);
    }

}
