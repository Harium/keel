package com.harium.keel.filter.selection.skin;

import com.harium.keel.filter.selection.HSVColorStrategy;
import org.junit.Assert;
import org.junit.Test;

public class HSVColorStrategyTest {

    private static final float EPSILON = 0.1f;

    @Test
    public void testConstructor() {
        float tolerance = 0.2f;

        HSVColorStrategy strategy = new HSVColorStrategy(tolerance);
        Assert.assertEquals(72, strategy.getHTolerance(), EPSILON);
        Assert.assertEquals(tolerance, strategy.getSTolerance(), EPSILON);
        Assert.assertEquals(tolerance, strategy.getVTolerance(), EPSILON);
    }

    @Test
    public void testConstructorWithIndividualTolerances() {
        float sTolerance = 0.2f;
        float vTolerance = 0.3f;

        HSVColorStrategy strategy = new HSVColorStrategy(99, sTolerance, vTolerance);
        Assert.assertEquals(99, strategy.getHTolerance(), EPSILON);
        Assert.assertEquals(sTolerance, strategy.getSTolerance(), EPSILON);
        Assert.assertEquals(vTolerance, strategy.getVTolerance(), EPSILON);
    }

}
