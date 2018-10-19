package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class ExtractBoundaryTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new ExtractBoundary();
        effect.apply(new MatrixSource(1, 2));
    }

    @Test
    public void testSimpleApplyGrayscale() {
        Effect effect = new ExtractBoundary();
        OneBandSource grayscale = new OneBandSource(1, 2);
        effect.apply(grayscale);
    }

}
