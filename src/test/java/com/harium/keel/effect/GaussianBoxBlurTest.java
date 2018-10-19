package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class GaussianBoxBlurTest {


    public static final int MIN_WIDTH = 3;
    public static final int MIN_HEIGHT = 3;

    @Test
    public void testSimpleApply() {
        Effect effect = new GaussianBoxBlur();
        effect.apply(new MatrixSource(MIN_WIDTH, MIN_HEIGHT));
    }

    @Test
    public void testSimpleApplyGrayscale() {
        Effect effect = new GaussianBoxBlur();
        OneBandSource grayscale = new OneBandSource(MIN_WIDTH, MIN_HEIGHT);
        effect.apply(grayscale);
    }

}
