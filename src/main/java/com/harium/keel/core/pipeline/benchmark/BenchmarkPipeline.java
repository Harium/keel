package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.pipeline.Pipeline;
import com.harium.keel.core.pipeline.PipelinePass;
import com.harium.keel.core.source.ImageSource;

public class BenchmarkPipeline<I, O> extends Pipeline<I, BenchmarkProcess<O>> {

    @SuppressWarnings("unchecked")
    public BenchmarkProcess<O> process(ImageSource source, I feature) {
        Object result = feature;

        BenchmarkProcess<O> process = new BenchmarkProcess();

        for (PipelinePass step : passes) {
            long start = System.currentTimeMillis();
            result = step.process(source, result);
            long end = System.currentTimeMillis();

            process.addStep(names.get(result), end - start);
        }

        return process;
    }

    public static BenchmarkPipeline<?, ?> build() {
        return new BenchmarkPipeline<>();
    }
}
