package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.pipeline.Pipeline;
import com.harium.keel.core.pipeline.PipelinePass;
import com.harium.keel.core.source.ImageSource;

public class BenchmarkPipeline<I, O> extends Pipeline<I, O> {

    private BenchmarkInfo<O> info = new BenchmarkInfo<>();

    @SuppressWarnings("unchecked")
    public O process(ImageSource source, I feature) {
        Object result = feature;

        info = new BenchmarkInfo();

        for (PipelinePass step : passes) {
            BenchmarkPass benchmarkPass = new BenchmarkPass(names.get(step), source);

            long start = System.currentTimeMillis();
            result = step.process(benchmarkPass.getSource(), result);
            long end = System.currentTimeMillis();

            benchmarkPass.setTime(end - start);
            addStep(benchmarkPass);

            if (ERROR == result) {
                result = null;
                break;
            }
        }

        info.setResult((O) result);
        return (O) result;
    }

    protected void addStep(BenchmarkPass pass) {
        info.addStep(pass);
    }

    public BenchmarkPipeline<I, O> build() {
        return new BenchmarkPipeline<>();
    }

    public BenchmarkInfo<O> getInfo() {
        return info;
    }
}
