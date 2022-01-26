package com.harium.keel.cluster;

public class Centroid<T> {

    public T value;
    public double[] features;

    public Centroid() {
    }

    public Centroid(double[] features) {
        this.features = features;
    }
}
