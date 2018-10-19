package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Test;

public class StampTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new Stamp();
        ((Stamp) effect).stamp(new MatrixSource(1, 1));
        effect.apply(new MatrixSource(1, 2));
    }

    @Test
    public void testUndefinedStamp() {
        Effect effect = new Stamp();
        try {
            effect.apply(new MatrixSource(1, 2));
            Assert.fail();
        } catch (RuntimeException e) {
            // Expected
        }
    }

}
