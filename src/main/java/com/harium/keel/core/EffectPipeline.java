package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;

import java.util.ArrayList;
import java.util.List;

public class EffectPipeline {

    protected List<Effect> effects = new ArrayList<Effect>();

    public EffectPipeline add(Effect effect) {
        effects.add(effect);
        return this;
    }

    public ImageSource process(ImageSource input) {
        ImageSource current = input;

        for (Effect effect : effects) {
            current = effect.apply(current);
        }

        return current;
    }

    public List<ImageSource> processBatch(ImageSource... inputs) {
        List<ImageSource> results = new ArrayList<>();

        for (ImageSource input : inputs) {
            ImageSource current = input;

            for (Effect effect : effects) {
                current = effect.apply(current);
            }

            results.add(current);
        }

        return results;
    }

}
