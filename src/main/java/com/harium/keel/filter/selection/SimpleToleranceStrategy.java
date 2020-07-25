package com.harium.keel.filter.selection;

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

}
