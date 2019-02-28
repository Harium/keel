package com.harium.keel.core.pipeline;

import com.harium.keel.core.Filter;
import com.harium.keel.core.source.ImageSource;

import java.util.List;

public class PipelineFilterPass<I, O> implements PipelinePass<I, List<O>> {

    private Filter<I, O> filter;

    public PipelineFilterPass() {
        super();
    }

    public PipelineFilterPass(Filter<I, O> filter) {
        super();
        this.filter = filter;
    }

    public List<O> process(ImageSource source, I feature) {
        return filter.filter(source, feature);
    }
}
