package com.harium.keel.filter.distance;

import com.harium.keel.core.helper.ColorHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EuclideanSquaredDistanceTest {

    private EuclideanSquaredDistance distance;

    @Before
    public void setUp() {
        distance = new EuclideanSquaredDistance();
    }

    @Test
    public void testMax() {
        float d = distance.distance(ColorHelper.getRGB(255,255,255), ColorHelper.getRGB(0,0,0));
        assertEquals(1, d, 0);
    }

    @Test
    public void testMin() {
        float d = distance.distance(ColorHelper.getRGB(0,0,0), ColorHelper.getRGB(0,0,0));
        assertEquals(0, d, 0);

        d = distance.distance(ColorHelper.getRGB(255,255,255), ColorHelper.getRGB(255,255,255));
        assertEquals(0, d, 0);
    }

}
