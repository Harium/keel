package com.harium.keel.core.strategy;

public abstract class BaseSelectionStrategy implements SelectionStrategy {

    protected float strength = 1f;
    protected boolean softSelection = false;

    @Override
    public void setStrength(float strength) {
        this.strength = strength;
    }

    public boolean isSoftSelection() {
        return softSelection;
    }

    @Override
    public void setSoftSelection(boolean softSelection) {
        this.softSelection = softSelection;
    }
}
