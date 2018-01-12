package com.harium.keel.filter.color;

import com.harium.keel.core.strategy.SelectionStrategy;

public abstract class SimpleToleranceStrategy implements SelectionStrategy {

    protected int tolerance = 0x42;

    public SimpleToleranceStrategy() {
        super();
    }

    public SimpleToleranceStrategy(int tolerance) {
        super();

        this.tolerance = tolerance;
    }

    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    @Override
    public boolean softValidateColor(int rgb, int j, int i, int reference) {
        return validateColor(rgb, j, i);
    }

}
