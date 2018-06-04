package com.harium.keel.core.helper;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Test;

public class ColorHelperTest {

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
}
