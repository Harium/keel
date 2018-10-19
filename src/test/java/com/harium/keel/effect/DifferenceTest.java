package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Test;

public class DifferenceTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new Difference();
        ((Difference) effect).setOverlayImage(new MatrixSource(1,2));
        effect.apply(new MatrixSource(1, 2));
    }

}
