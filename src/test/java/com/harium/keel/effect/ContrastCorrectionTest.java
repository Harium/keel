package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Assert;
import org.junit.Test;

public class ContrastCorrectionTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new ContrastCorrection();
        effect.apply(new MatrixSource(1, 2));
    }

}
