package com.harium.keel.core.pipeline;

import com.harium.keel.core.Effect;
import com.harium.keel.core.Filter;
import com.harium.keel.core.Modifier;

/**
 * A Fluent API filter pipeline
 */
public class FilterPipeline<I, O> extends Pipeline<I, O> {

    public FilterPipeline add(Filter filter) {
        return add("", filter);
    }

    public FilterPipeline add(String name, Filter filter) {
        PipelinePass pass = new PipelineFilterPass(filter);
        add(name, pass);
        return this;
    }

    public FilterPipeline add(Modifier modifier) {
        return add("", modifier);
    }

    public FilterPipeline add(String name, Modifier modifier) {
        PipelinePass step = new PipelineModifierPass(modifier);
        add(name, step);
        return this;
    }

    public FilterPipeline add(Effect effect) {
        return add("", effect);
    }

    public FilterPipeline add(String name, Effect effect) {
        add(name, new PipelineEffectPass(effect));
        return this;
    }

    public static FilterPipeline<?, ?> build() {
        return new FilterPipeline<>();
    }
}
