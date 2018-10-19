package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class BinaryWatershedTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new BinaryWatershed();
        OneBandSource grayscale = new OneBandSource(1,2);
        effect.apply(grayscale);
    }

}
