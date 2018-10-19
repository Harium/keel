package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class GammaCorrectionTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new GammaCorrection();
        effect.apply(new MatrixSource(1, 2));
    }

    @Test
    public void testSimpleApplyGrayscale() {
        Effect effect = new GammaCorrection();
        OneBandSource grayscale = new OneBandSource(1, 2);
        effect.apply(grayscale);
    }

}
