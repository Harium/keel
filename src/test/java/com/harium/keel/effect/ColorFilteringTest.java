package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Assert;
import org.junit.Test;

public class ColorFilteringTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new ColorFiltering();
        effect.apply(new MatrixSource(1, 2));
    }

    @Test
    public void testSimpleApplyGrayscale() {
        Effect effect = new ColorFiltering();
        OneBandSource grayscale = new OneBandSource(1,2);
        try {
            effect.apply(grayscale);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // Grayscale Image
        }
    }

}
