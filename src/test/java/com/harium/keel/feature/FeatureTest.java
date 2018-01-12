package com.harium.keel.feature;

import org.junit.Assert;
import org.junit.Test;

public class FeatureTest {

    @Test
    public void testMerge() {
        Feature a = new Feature(2,3,11,10);
        Feature b = new Feature(5,5,10,10);

        a.merge(b);

        Assert.assertEquals(2, a.x);
        Assert.assertEquals(3, a.y);
        Assert.assertEquals(13, a.width);
        Assert.assertEquals(12, a.height);
    }

}
