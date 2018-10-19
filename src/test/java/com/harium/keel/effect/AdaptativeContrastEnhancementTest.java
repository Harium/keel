package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;
import org.junit.Test;

public class AdaptativeContrastEnhancementTest {

    @Test
    public void testSimpleApply() {
        Effect effect = new ArtifactsRemoval();
        OneBandSource grayscale = new OneBandSource(1,2);
        effect.apply(grayscale);
    }

}
