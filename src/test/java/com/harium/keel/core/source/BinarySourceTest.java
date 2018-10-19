package com.harium.keel.core.source;

import org.junit.Assert;
import org.junit.Test;

public class BinarySourceTest {

    @Test
    public void testToBinary() {
        Assert.assertEquals(0, BinarySource.toBinary(0));
        Assert.assertEquals(0, BinarySource.toBinary(1));
        Assert.assertEquals(0, BinarySource.toBinary(254));
        Assert.assertEquals(1, BinarySource.toBinary(255));
    }

    @Test
    public void testToRGB() {
        BinarySource source = new BinarySource(1, 1, new byte[]{1});

        ImageSource output = source.toGrayScaleRGB();

        Assert.assertEquals(255, output.getR(0, 0));
        Assert.assertEquals(255, output.getG(0, 0));
        Assert.assertEquals(255, output.getB(0, 0));
    }


}
