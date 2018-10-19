package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class BinaryClosingTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new BinaryClosing();
        OneBandSource grayscale = new OneBandSource(1,2);
        effect.apply(grayscale);
    }

}
