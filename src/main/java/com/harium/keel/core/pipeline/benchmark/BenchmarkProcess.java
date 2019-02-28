package com.harium.keel.core.pipeline.benchmark;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkProcess<O> {

    private List<BenchmarkStep> benchmarkSteps = new ArrayList<>();

    O result;

    public O getResult() {
        return result;
    }

    public void setResult(O result) {
        this.result = result;
    }

    public void addStep(String name, long time) {
        benchmarkSteps.add(new BenchmarkStep(name, time));
    }

    public List<BenchmarkStep> getBenchmarkSteps() {
        return benchmarkSteps;
    }
}
