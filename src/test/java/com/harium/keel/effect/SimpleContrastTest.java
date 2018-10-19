package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Test;

public class SimpleContrastTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new SimpleContrast();
        effect.apply(new MatrixSource(1, 2));
    }

}
