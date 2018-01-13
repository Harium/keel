package com.harium.keel.core.pipeline;

import com.harium.keel.core.Filter;
import com.harium.keel.core.Modifier;
import com.harium.keel.core.source.ImageSource;

public class FilterPipelineStep<I, O> {

    Filter<I, ?> filter;
    Modifier<Object, O> modifier;

    FilterPipelineStep() {
        super();
    }

    FilterPipelineStep(Filter<I, ?> filter) {
        super();
        this.filter = filter;
    }

    public FilterPipelineStep(Filter<I, ?> filter, Modifier<Object, O> modifier) {
        super();
        this.filter = filter;
        this.modifier = modifier;
    }

    @SuppressWarnings("unchecked")
    public O run(ImageSource source, I feature) {
        Object out = filter.filter(source, feature);

        if (modifier != null) {
            return modifier.modify(out);
        }

        return (O) out;
    }
}
