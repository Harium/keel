package com.harium.keel.core.pipeline.benchmark;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkInfo<O> {

    private List<BenchmarkStep> steps = new ArrayList<>();

    private O result;

    public O getResult() {
        return result;
    }

    public void setResult(O result) {
        this.result = result;
    }

    public void addStep(BenchmarkStep step) {
        steps.add(step);
    }

    public List<BenchmarkStep> getSteps() {
        return steps;
    }
}
