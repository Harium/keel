package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.source.ImageSource;

public class BenchmarkPass {

    private String name;
    private long time;
    private BenchmarkSource source;

    public BenchmarkPass(String name, ImageSource original) {
        this.name = name;
        source = new BenchmarkSource(original);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ImageSource getSource() {
        return source;
    }
}
