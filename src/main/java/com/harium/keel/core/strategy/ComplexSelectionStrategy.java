package com.harium.keel.core.strategy;

import java.util.ArrayList;
import java.util.List;

public class ComplexSelectionStrategy extends BaseSelectionStrategy {

    private List<SelectionStrategy> strategies = new ArrayList<SelectionStrategy>();

    public ComplexSelectionStrategy() {
        super();
    }

    public List<SelectionStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<SelectionStrategy> strategies) {
        this.strategies = strategies;
    }

    public void addStrategy(SelectionStrategy strategy) {
        this.strategies.add(strategy);
    }

    @Override
    public boolean valid(int rgb, int j, int i) {
        if (!validateColorChildren(rgb, j, i)) {
            return false;
        }
        return true;
    }

    private boolean validateColorChildren(int rgb, int j, int i) {
        for (SelectionStrategy strategy : strategies) {
            if (!strategy.valid(rgb, j, i)) {
                return false;
            }
        }
        return true;
    }

}
