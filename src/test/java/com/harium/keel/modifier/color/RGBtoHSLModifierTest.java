package com.harium.keel.modifier.color;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RGBtoHSLModifierTest {

    private static final float EPSILON = 0.1f;
    private static final float HUE_RANGE = 360f;

    @Test
    public void testConversion() {
        // Red
        assertColor(255, 0, 0);
        // Green
        assertColor(0, 255, 0);
        // Blue
        assertColor(0, 0, 255);
        // White
        assertColor(255, 255, 255);
        // Black
        assertColor(0, 0, 0);
        // Custom Colors
        assertColor(Color.ALICE_BLUE);
        assertColor(Color.BLUE_VIOLET);
        assertColor(Color.CRIMSON);
        assertColor(Color.CYAN);
        assertColor(Color.DARK_GOLDENROD);
        assertColor(Color.DEEP_PINK);
        assertColor(Color.GREEN_ETYL);
        assertColor(Color.KHAKI);
        assertColor(Color.LAVENDER);
        assertColor(Color.LIME_GREEN);
        assertColor(Color.VIOLET);
    }

    private void assertColor(Color color) {
        assertColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    private void assertColor(int r, int g, int b) {
        float[] hsl = RGBtoHSLModifier.getHSLArray(r, g, b);
        float[] correct = ColorHelper.getHSLArray(r, g, b);

        assertEquals(correct[0] / HUE_RANGE, hsl[0], EPSILON);
        assertEquals(correct[1], hsl[1], EPSILON);
        assertEquals(correct[2], hsl[2], EPSILON);
    }

}
