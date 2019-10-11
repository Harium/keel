package com.harium.keel.core.strategy;

public abstract class BaseSelectionStrategy implements SelectionStrategy {

    protected float strength = 1f;

    @Override
    public void setBaseRGB(int baseRGB) {

    }

    @Override
    public void setStrength(float strength) {
        this.strength = strength;
    }
}
