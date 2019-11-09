package com.harium.keel.processor;

public abstract class BaseProcessor<F, T> implements com.harium.keel.core.Processor<T> {

    protected F feature;

    public BaseProcessor feature(F feature) {
        this.feature = feature;
        return this;
    }

}
