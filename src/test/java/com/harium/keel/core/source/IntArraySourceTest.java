package com.harium.keel.core.source;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntArraySourceTest {

    private IntArraySource source;

    private final int SOURCE_WIDTH = 2;
    private final int SOURCE_HEIGHT = 2;

    @Before
    public void setUp() {
        int[] array = {Color.BLACK.getRGB(), Color.RED.getRGB(), Color.BLUE.getRGB(), Color.WHITE.getRGB()};
        source = new IntArraySource(SOURCE_WIDTH, SOURCE_HEIGHT, array);
    }

    @Test
    public void testGetRGB() {
        Assert.assertEquals(Color.BLACK.getRGB(), source.getRGB(0, 0));
        Assert.assertEquals(Color.RED.getRGB(), source.getRGB(1, 0));
        Assert.assertEquals(Color.BLUE.getRGB(), source.getRGB(0, 1));
        Assert.assertEquals(Color.WHITE.getRGB(), source.getRGB(1, 1));

        Assert.assertNotEquals(Color.BLUE.getRGB(), source.getRGB(1, 0));
    }

    @Test
    public void testSetRGB() {
        int x = 1, y = 1, color = Color.GREEN.getRGB();
        source.setRGB(x, y, color);

        Assert.assertEquals(color, source.getRGB(x, y));
    }

}
