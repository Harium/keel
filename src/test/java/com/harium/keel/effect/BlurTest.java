package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class BlurTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new Blur();
        MatrixSource grayscale = new MatrixSource(1,2);
        effect.apply(grayscale);
    }

}
