package com.harium.keel.filter.smooth;

public interface SmoothFilter {

    double smooth(double value);

    void setInitialValue(double value);

}
