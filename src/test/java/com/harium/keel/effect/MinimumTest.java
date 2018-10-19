package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Test;

public class MinimumTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new Minimum();
        effect.apply(new MatrixSource(1, 2));
    }

}
