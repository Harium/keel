package com.harium.keel.core.helper;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class ColorHelperTest {
    @Test
    public void testGetRGB() {
        Assert.assertEquals(Color.RED.getRGB(), ColorHelper.getRGB(0xff, 0, 0));
        Assert.assertEquals(Color.GREEN.getRGB(), ColorHelper.getRGB(0, 0xff, 0));
        Assert.assertEquals(Color.BLUE.getRGB(), ColorHelper.getRGB(0, 0, 0xff));
    }
}
