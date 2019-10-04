package com.harium.keel.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterpolatorUtilTest {

    @Test
    public void testScale() {
        assertEquals(0.5f, InterpolatorUtil.changeScale(0f, 1f, 0.5f), 0);
        assertEquals(1f, InterpolatorUtil.changeScale(0f, 0.5f, 0.5f), 0);
        assertEquals(0.5f, InterpolatorUtil.changeScale(0f, 0.5f, 0.25f), 0);
        assertEquals(0.5f, InterpolatorUtil.changeScale(0.5f, 1f, 0.75f), 0);
    }

}
