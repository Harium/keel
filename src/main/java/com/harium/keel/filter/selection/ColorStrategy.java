package com.harium.keel.filter.selection;

import com.harium.keel.core.strategy.SelectionStrategy;

public interface ColorStrategy extends SelectionStrategy {
    void setColor(int color);
    int getColor();
}
