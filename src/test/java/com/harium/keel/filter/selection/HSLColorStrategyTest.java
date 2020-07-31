package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HSLColorStrategyTest {

    private static final float EPSILON = 0.1f;

    @Test
    public void testConstructorMaxLowerThenMin() {
        float tolerance = 0.2f;

        HSLColorStrategy strategy = new HSLColorStrategy(Color.RED.getRGB(), tolerance);
        Assert.assertEquals(0.8f, strategy.minH, EPSILON);
        Assert.assertEquals(0.2f, strategy.maxH, EPSILON);// maxH < minH is expected since hue is circular
        Assert.assertEquals(0.8f, strategy.minS, EPSILON);
        Assert.assertEquals(1.0f, strategy.maxS, EPSILON);
        Assert.assertEquals(0.3f, strategy.minL, EPSILON);
        Assert.assertEquals(0.7f, strategy.maxL, EPSILON);

        assertTrue(strategy.valid(ColorHelper.fromHSL(0.1f*360, 0.9f,0.5f), 0, 0));
    }

    @Test
    public void testConstructorWithIndividualTolerances() {
        float sTolerance = 0.2f;
        float lTolerance = 0.3f;

        HSLColorStrategy strategy = new HSLColorStrategy(Color.BLUE.getRGB(), 0.001f, sTolerance, lTolerance);
        Assert.assertEquals(0.6656667f, strategy.minH, EPSILON);
        Assert.assertEquals(0.6676667f, strategy.maxH, EPSILON);// This is expected since hue is circular
        Assert.assertEquals(0.8f, strategy.minS, EPSILON);
        Assert.assertEquals(1.0f, strategy.maxS, EPSILON);
        Assert.assertEquals(0.1999999f, strategy.minL, EPSILON);
        Assert.assertEquals(0.8f, strategy.maxL, EPSILON);

        assertTrue(strategy.valid(ColorHelper.fromHSL(0.666f*360, 0.9f,0.5f), 0, 0));
    }

}
