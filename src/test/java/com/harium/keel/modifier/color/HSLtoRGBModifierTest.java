package com.harium.keel.modifier.color;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HSLtoRGBModifierTest {

    private static final float HUE_RANGE = 360f;
    public static final int EPSILON = 1;

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
        float h = hsl[0];
        float s = hsl[1];
        float l = hsl[2];

        int rgb = HSLtoRGBModifier.hslToRgb(hsl);
        int correct = ColorHelper.fromHSL(h * HUE_RANGE, s, l);

        assertEquals(ColorHelper.getRed(correct), ColorHelper.getRed(rgb), EPSILON);
        assertEquals(ColorHelper.getGreen(correct), ColorHelper.getGreen(rgb), EPSILON);
        assertEquals(ColorHelper.getBlue(correct), ColorHelper.getBlue(rgb), EPSILON);
    }

}
