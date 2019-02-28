package com.harium.keel.core.pipeline;

import com.harium.keel.core.source.ImageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Fluent API filter pipeline
 */
public class Pipeline<I, T> {

    protected List<PipelinePass<?, ?>> passes = new ArrayList<>();
    protected Map<PipelinePass, String> names = new HashMap<>();

    public Pipeline add(PipelinePass pass) {
        return add("", pass);
    }

    public Pipeline add(String name, PipelinePass pass) {
        passes.add(pass);
        names.put(pass, name);
        return this;
    }

    @SuppressWarnings("unchecked")
    public T process(ImageSource source, I feature) {
        Object result = feature;

        for (PipelinePass step : passes) {
            result = step.process(source, result);
        }

        return (T) result;
    }

    public static Pipeline<?, ?> build() {
        return new Pipeline<>();
    }
}
