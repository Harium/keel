package com.harium.keel.effect.noise;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Test;

public class AdditiveNoiseTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new AdditiveNoise();
        effect.apply(new MatrixSource(1,2));
    }

}
