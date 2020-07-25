package com.harium.keel.filter.smooth;

public class ExponentialSmooth implements SmoothFilter {

    double initialValue = 0;
    double alpha = 0.5;

    public ExponentialSmooth(double alpha) {
        this.alpha = alpha;
    }

    public ExponentialSmooth(double alpha, double initialValue) {
        this.alpha = alpha;
        this.initialValue = initialValue;
    }

    @Override
    public double smooth(double value) {
        double y = alpha * value + (1 - alpha) * initialValue;
        initialValue = y;
        return y;
    }

    @Override
    public void setInitialValue(double value) {
        this.initialValue = value;
    }

}
