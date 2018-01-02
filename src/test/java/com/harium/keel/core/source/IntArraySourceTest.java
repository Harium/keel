package com.harium.keel.core.source;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntArraySourceTest {

    private IntArraySource arraySource;

    private final int SOURCE_WIDTH = 2;
    private final int SOURCE_HEIGHT = 2;

    @Before
    public void setUp() {
        int[] array = {Color.BLACK.getRGB(), Color.RED.getRGB(), Color.BLUE.getRGB(), Color.WHITE.getRGB()};
        arraySource = new IntArraySource(SOURCE_WIDTH, SOURCE_HEIGHT, array);
    }

    @Test
    public void testGetRgb() {
        Assert.assertEquals(Color.BLACK.getRGB(), arraySource.getRGB(0, 0));
        Assert.assertEquals(Color.RED.getRGB(), arraySource.getRGB(1, 0));
        Assert.assertEquals(Color.BLUE.getRGB(), arraySource.getRGB(0, 1));
        Assert.assertEquals(Color.WHITE.getRGB(), arraySource.getRGB(1, 1));

        Assert.assertNotEquals(Color.BLUE.getRGB(), arraySource.getRGB(1, 0));
    }

}
