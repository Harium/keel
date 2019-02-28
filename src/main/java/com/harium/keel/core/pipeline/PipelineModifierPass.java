package com.harium.keel.core.pipeline;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.source.ImageSource;

public class PipelineModifierPass<I, O> implements PipelinePass<I, O> {

    private Modifier<I, O> modifier;

    public PipelineModifierPass(Modifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public O process(ImageSource source, I input) {
        return modifier.modify(input);
    }
}
