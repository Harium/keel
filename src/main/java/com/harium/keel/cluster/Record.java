package com.harium.keel.cluster;

public class Record<T> {

    private final T value;
    private final double[] features;

    public Record(T value, double[] features) {
        this.value = value;
        this.features = features;
    }

    public T getValue() {
        return value;
    }

    public double[] getFeatures() {
        return features;
    }
}
