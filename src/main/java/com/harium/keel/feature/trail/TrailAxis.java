package com.harium.keel.feature.trail;

import java.util.ArrayList;
import java.util.List;

public class TrailAxis {

    private int size;
    private List<Double> values;
    private double lastValue = 0;

    public TrailAxis(int size) {
        super();
        this.size = size;

        reset(size);
    }

    private void reset(int size) {
        values = new ArrayList<Double>(size);

        for (int i = 0; i < size; i++) {
            values.add(0d);
        }
    }

    public void addValue(double value) {
        values.remove(0);
        values.add(value);
        lastValue = value;
    }

    public List<Double> getValues() {
        return values;
    }

    public double getDelta() {
        return lastValue - values.get(size - 2);
    }

    public double getDeltaMod() {
        double delta = getDelta();

        if (delta < 0) {
            return -delta;
        }

        return delta;
    }

}
