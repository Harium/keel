package com.harium.keel.filter.selection;

import com.harium.keel.core.strategy.BaseSelectionStrategy;

public abstract class SimpleToleranceStrategy extends BaseSelectionStrategy {

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
    public void setBaseRGB(int baseRGB) {

    }
}
