package com.harium.keel.filter.color.skin;

import com.harium.keel.filter.color.HSLColorStrategy;
import com.harium.keel.filter.color.HSVColorStrategy;
import org.junit.Assert;
import org.junit.Test;

public class HSLColorStrategyTest {

    private static final float EPSILON = 0.1f;

    @Test
    public void testConstructor() {
        float tolerance = 0.2f;

        HSLColorStrategy strategy = new HSLColorStrategy(tolerance);
        Assert.assertEquals(72, strategy.getHTolerance(), EPSILON);
        Assert.assertEquals(tolerance, strategy.getSTolerance(), EPSILON);
        Assert.assertEquals(tolerance, strategy.getLTolerance(), EPSILON);
    }


    @Test
    public void testConstructorWithIndividualTolerances() {
        float sTolerance = 0.2f;
        float lTolerance = 0.3f;

        HSLColorStrategy strategy = new HSLColorStrategy(99, sTolerance, lTolerance);
        Assert.assertEquals(99, strategy.getHTolerance(), EPSILON);
        Assert.assertEquals(sTolerance, strategy.getSTolerance(), EPSILON);
        Assert.assertEquals(lTolerance, strategy.getLTolerance(), EPSILON);
    }

}
