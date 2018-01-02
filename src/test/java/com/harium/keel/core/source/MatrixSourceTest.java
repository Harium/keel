package com.harium.keel.core.source;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MatrixSourceTest {

    private MatrixSource checkerSource;

    private final int SOURCE_WIDTH = 2;
    private final int SOURCE_HEIGHT = 2;

    @Before
    public void setUp() {
        checkerSource = new MatrixSource(SOURCE_WIDTH, SOURCE_HEIGHT);
        checkerSource.getMatrix()[0][0] = Color.BLACK.getRGB();
        checkerSource.getMatrix()[1][0] = Color.WHITE.getRGB();
        checkerSource.getMatrix()[0][1] = Color.WHITE.getRGB();
        checkerSource.getMatrix()[1][1] = Color.BLACK.getRGB();
    }

    @Test
    public void testGetRgb() {
        Assert.assertEquals(Color.BLACK.getRGB(), checkerSource.getRGB(0, 0));
        Assert.assertEquals(Color.BLACK.getRGB(), checkerSource.getRGB(1, 1));
        Assert.assertEquals(Color.WHITE.getRGB(), checkerSource.getRGB(0, 1));
        Assert.assertEquals(Color.WHITE.getRGB(), checkerSource.getRGB(1, 0));

        Assert.assertNotEquals(Color.BLUE.getRGB(), checkerSource.getRGB(1, 0));
    }

}
