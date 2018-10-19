package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Assert;
import org.junit.Test;

public class HysteresisThresholdTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new HysteresisThreshold();
        try {
            effect.apply(new MatrixSource(1, 2));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Grayscale
        }
    }

    @Test
    public void testSimpleApplyGrayscale() {
        Effect effect = new HysteresisThreshold();
        OneBandSource grayscale = new OneBandSource(1, 2);
        effect.apply(grayscale);
    }

}
