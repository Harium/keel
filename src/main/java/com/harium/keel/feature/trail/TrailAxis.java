package com.harium.keel.feature.trail;

import java.util.ArrayList;
import java.util.List;

public class TrailAxis {

    private int size;
    private List<Float> values;
    private float lastValue = 0;

    public TrailAxis(int size) {
        super();
        this.size = size;

        reset(size);
    }

    private void reset(int size) {
        values = new ArrayList<Float>(size);

        for (int i = 0; i < size; i++) {
            values.add(0f);
        }
    }

    public void addValue(float value) {
        values.remove(0);
        values.add(value);
        lastValue = value;
    }

    public List<Float> getValues() {
        return values;
    }

    public float getDelta() {
        return lastValue - values.get(size - 2);
    }

    public float getDeltaMod() {
        float delta = getDelta();

        if (delta < 0) {
            return -delta;
        }

        return delta;
    }

}
