package com.harium.keel.core.source;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class RGBByteArraySourceTest {

    RGBByteArraySource source;

    @Before
    public void setUp() {
        int width = 5;
        int height = 2;
        byte[] rgb = new byte[30];
        rgb[0] = 127;
        rgb[1] = -128;
        rgb[2] = -128;

        rgb[15] = 127;
        rgb[16] = 127;
        rgb[17] = 127;

        source = new RGBByteArraySource(width, height, rgb);
    }

    @Test
    public void testGetRGB() {
        Assert.assertEquals(Color.RED.getRGB(), source.getRGB(0,0));
        Assert.assertEquals(Color.WHITE.getRGB(), source.getRGB(5,0));
    }

}
