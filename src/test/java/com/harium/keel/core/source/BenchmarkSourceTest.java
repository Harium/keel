package com.harium.keel.core.source;

import com.harium.etyl.commons.graphics.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BenchmarkSourceTest {

    MatrixSource original;
    BenchmarkSource source;

    @Before
    public void setUp() {
        original = new MatrixSource(2,2);
        source = new BenchmarkSource(original);
    }

    @Test
    public void testOperations() {
        source.getRGB(0,0);
        Assert.assertEquals(1, source.getOperations());
        source.setRGB(0,0, Color.BLACK.getRGB());
        Assert.assertEquals(2, source.getOperations());
        Assert.assertEquals(Color.BLACK.getRGB(), original.getRGB(0,0));
    }

}
