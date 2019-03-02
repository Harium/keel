package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.pipeline.Pipeline;
import com.harium.keel.core.pipeline.PipelinePass;
import com.harium.keel.core.source.ImageSource;

public class BenchmarkPipeline<I, O> extends Pipeline<I, O> {

    private BenchmarkInfo<O> info = new BenchmarkInfo();

    @SuppressWarnings("unchecked")
    public O process(ImageSource source, I feature) {
        Object result = feature;

        info = new BenchmarkInfo();

        for (PipelinePass step : passes) {
            BenchmarkStep benchmarkStep = new BenchmarkStep(names.get(step), source);

            long start = System.currentTimeMillis();
            result = step.process(benchmarkStep.getSource(), result);
            long end = System.currentTimeMillis();

            benchmarkStep.setTime(end - start);

            info.addStep(benchmarkStep);
        }

        info.setResult((O) result);
        return (O) result;
    }

    public BenchmarkPipeline<I, O> build() {
        return new BenchmarkPipeline<>();
    }

    public BenchmarkInfo getInfo() {
        return info;
    }
}
