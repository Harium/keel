package com.harium.keel.effect.noise;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Test;

public class SaltAndPepperNoiseTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new SaltAndPepperNoise();
        effect.apply(new MatrixSource(1,2));
    }

}
