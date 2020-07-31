package com.harium.keel.filter.selection;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmoothColorStrategyTest {

    SmoothColorStrategy smoothColorStrategy;

    @Before
    public void setUp() {
        smoothColorStrategy = new SmoothColorStrategy(new RGBColorStrategy(Color.WHITE.getRGB(), 255));
    }

    @Test
    public void testSmooth() {
        assertTrue(smoothColorStrategy.valid(Color.LIGHT_GRAY.getRGB(),0,0));
        int r = ColorHelper.getRed(smoothColorStrategy.getColor());
        int g = ColorHelper.getGreen(smoothColorStrategy.getColor());
        int b = ColorHelper.getBlue(smoothColorStrategy.getColor());
        assertEquals(105, r);
        assertEquals(105, g);
        assertEquals(105, b);
    }

    @Test
    public void testSmooth_InitialColor() {
        smoothColorStrategy.setColor(Color.RED.getRGB());
        assertTrue(smoothColorStrategy.valid(Color.PINK.getRGB(),0,0));
        int r = ColorHelper.getRed(smoothColorStrategy.getColor());
        int g = ColorHelper.getGreen(smoothColorStrategy.getColor());
        int b = ColorHelper.getBlue(smoothColorStrategy.getColor());
        assertEquals(255, r);
        assertEquals(96, g);
        assertEquals(101, b);
    }

}
