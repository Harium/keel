package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;

import java.util.ArrayList;
import java.util.List;

/**
 * A filter pipeline with Fluent API
 */
public class FilterPipeline<I, T> {

    List<FilterPipelineStep<?, ?>> steps = new ArrayList<>();

    public FilterPipeline add(FilterPipelineStep step) {
        steps.add(step);
        return this;
    }

    public FilterPipeline add(Filter filter) {
        FilterPipelineStep step = new FilterPipelineStep(filter);
        steps.add(step);
        return this;
    }

    public FilterPipeline add(Filter filter, Modifier modifier) {
        FilterPipelineStep step = new FilterPipelineStep(filter, modifier);
        steps.add(step);
        return this;
    }

    @SuppressWarnings("unchecked")
    public T run(ImageSource source, I feature) {
        Object result = feature;

        for (FilterPipelineStep step : steps) {
            result = step.run(source, result);
        }

        return (T) result;
    }

    public static FilterPipeline<?, ?> build() {
        return new FilterPipeline<>();
    }
}
