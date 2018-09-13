package com.harium.keel.core.helper;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Test;

public class ColorHelperTest {

    private static final float EPSILON = 0.1f;
    private static final float HUE_TOLERANCE = 1;

    @Test
    public void testGetRGB() {
        Assert.assertEquals(new Color(0xff, 0, 0).getRGB(), ColorHelper.getRGB(0xff, 0, 0));
        Assert.assertEquals(new Color(0, 0xff, 0).getRGB(), ColorHelper.getRGB(0, 0xff, 0));
        Assert.assertEquals(new Color(0, 0, 0xff).getRGB(), ColorHelper.getRGB(0, 0, 0xff));
    }

    @Test
    public void testGetRed() {
        Assert.assertEquals(0xff, ColorHelper.getRed(Color.WHITE.getRGB()));
        Assert.assertEquals(0xff, ColorHelper.getRed(new Color(0xff, 0, 0).getRGB()));
    }

    @Test
    public void testGetGreen() {
        Assert.assertEquals(0xff, ColorHelper.getGreen(Color.WHITE.getRGB()));
        Assert.assertEquals(0xff, ColorHelper.getGreen(new Color(0, 0xff, 0).getRGB()));
    }

    @Test
    public void testGetBlue() {
        Assert.assertEquals(0xff, ColorHelper.getBlue(Color.WHITE.getRGB()));
        Assert.assertEquals(0xff, ColorHelper.getBlue(new Color(0, 0, 0xff).getRGB()));
    }

    @Test
    public void testGetY() {
        Assert.assertEquals(0xff, ColorHelper.getY(Color.WHITE.getRGB()));
    }

    @Test
    public void testClamp() {
        Assert.assertEquals(0xff, ColorHelper.clamp(999));
        Assert.assertEquals(0xff, ColorHelper.clamp(0xff));
        Assert.assertEquals(0xfe, ColorHelper.clamp(0xfe));
        Assert.assertEquals(1, ColorHelper.clamp(1));
        Assert.assertEquals(0, ColorHelper.clamp(0));
        Assert.assertEquals(0, ColorHelper.clamp(-1));
    }

    @Test
    public void testGetHSV() {
        Color color = Color.GREEN_ETYL;

        float[] hsvArray = ColorHelper.getHSVArray(color.getRGB());

        Assert.assertEquals(149.811, hsvArray[0], EPSILON);
        Assert.assertEquals(0.6652, hsvArray[1], EPSILON);
        Assert.assertEquals(0.9372, hsvArray[2], EPSILON);
    }

    @Test
    public void testGetHSVWithoutSaturation() {
        Color color = Color.WHITE;

        float[] hsvArray = ColorHelper.getHSVArray(color.getRGB());

        Assert.assertEquals(0, hsvArray[0], EPSILON);
        Assert.assertEquals(0, hsvArray[1], EPSILON);
        Assert.assertEquals(1, hsvArray[2], EPSILON);
    }

    @Test
    public void fromHSV() {
        Color color = Color.GREEN_ETYL;

        float[] hsvArray = ColorHelper.getHSVArray(color.getRGB());

        int back = ColorHelper.fromHSV(hsvArray[0], hsvArray[1], hsvArray[2]);
        Assert.assertEquals(ColorHelper.getRed(back), color.getRed(), 1);
        Assert.assertEquals(ColorHelper.getGreen(back), color.getGreen(), 1);
        Assert.assertEquals(ColorHelper.getBlue(back), color.getBlue(), 1);
    }

    /**
     * Test case from: https://www.programmingalgorithms.com/algorithm/hsl-to-rgb?lang=C%2B%2B
     */
    @Test
    public void testGetHSL() {
        Color color = Color.GREEN_ETYL;

        float[] hslArray = ColorHelper.getHSLArray(color.getRGB());

        Assert.assertEquals(149.811, hslArray[0], HUE_TOLERANCE);
        Assert.assertEquals(0.6652, hslArray[1], HUE_TOLERANCE);
        Assert.assertEquals(0.9372, hslArray[2], HUE_TOLERANCE);
    }

    @Test
    public void testGetHSLFromChannels() {
        float[] hslArray = ColorHelper.getHSLArray(82, 0, 87);

        Assert.assertEquals(296, hslArray[0], EPSILON);
        Assert.assertEquals(1, hslArray[1], EPSILON);
        Assert.assertEquals(0.17058824f, hslArray[2], EPSILON);
    }

    @Test
    public void fromHSL() {
        int back = ColorHelper.fromHSL(138, 0.50f, 0.76f);

        Assert.assertEquals(163, ColorHelper.getRed(back), HUE_TOLERANCE);
        Assert.assertEquals(224, ColorHelper.getGreen(back), HUE_TOLERANCE);
        Assert.assertEquals(181, ColorHelper.getBlue(back), HUE_TOLERANCE);
    }

    @Test
    public void testFromYCbCr() {
        Color color = Color.GREEN_ETYL;

        int y = ColorHelper.getY(color.getRGB());
        int cb = ColorHelper.getCB(color.getRGB());
        int cr = ColorHelper.getCR(color.getRGB());

        Color back = new Color(ColorHelper.fromYCbCr(y, cb, cr));

        Assert.assertEquals(ColorHelper.getRed(back.getRGB()), ColorHelper.getRed(color.getRGB()), 5);
        Assert.assertEquals(ColorHelper.getGreen(back.getRGB()), ColorHelper.getGreen(color.getRGB()), 5);
        Assert.assertEquals(ColorHelper.getBlue(back.getRGB()), ColorHelper.getBlue(color.getRGB()), 5);
    }
}
