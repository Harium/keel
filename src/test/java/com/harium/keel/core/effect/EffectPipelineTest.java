package com.harium.keel.core.effect;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EffectPipelineTest {

    EffectPipeline pipeline;

    @Before
    public void setUp() {
        pipeline = new EffectPipeline();
    }

    @Test
    public void testAdd() {
        Effect e1 = new BlackWhiteAverageEffect();
        Effect e2 = new BlackWhiteAverageEffect();

        pipeline.add(e1).add(e2);

        Assert.assertEquals(e1, pipeline.effects.get(0));
        Assert.assertEquals(e2, pipeline.effects.get(1));
    }

    @Test
    public void testProcess() {
        EffectPipeline pipeline = new EffectPipeline();

        Effect e = mock(Effect.class);

        ImageSource input = createSource();

        pipeline.add(e).process(input);

        verify(e, times(1)).apply(any(ImageSource.class));
    }

    @Test
    public void testProcessBatch() {
        EffectPipeline pipeline = new EffectPipeline();

        Effect e1 = mock(Effect.class);
        Effect e2 = mock(Effect.class);

        ImageSource input = createSource();

        pipeline.add(e1).add(e2).processBatch(input, input);

        verify(e1, times(2)).apply(any(ImageSource.class));
        verify(e2, times(2)).apply(any(ImageSource.class));
    }

    private ImageSource createSource() {
        int[][] out = new int[1][1];
        out[0][0] = ColorHelper.getRGB(0xff,0,0);
        return new MatrixSource(out);
    }

}
