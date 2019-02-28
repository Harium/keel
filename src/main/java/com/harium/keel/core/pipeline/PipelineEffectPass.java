package com.harium.keel.core.pipeline;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;

public class PipelineEffectPass implements PipelinePass<ImageSource, ImageSource> {

    private Effect effect;

    public PipelineEffectPass(Effect effect) {
        this.effect = effect;
    }

    @Override
    public ImageSource process(ImageSource source, ImageSource feature) {
        return effect.apply(feature);
    }
}
