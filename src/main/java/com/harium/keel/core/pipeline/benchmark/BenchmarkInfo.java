package com.harium.keel.core.pipeline.benchmark;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkInfo<O> {

    private List<BenchmarkPass> steps = new ArrayList<>();

    private O result;

    public O getResult() {
        return result;
    }

    public void setResult(O result) {
        this.result = result;
    }

    public void addStep(BenchmarkPass step) {
        steps.add(step);
    }

    public List<BenchmarkPass> getPasses() {
        return steps;
    }
}
