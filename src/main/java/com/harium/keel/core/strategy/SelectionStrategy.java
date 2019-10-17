package com.harium.keel.core.strategy;


public interface SelectionStrategy {
    boolean valid(int rgb, int x, int y);

    void setBaseRGB(int baseRGB);

    void setStrength(float strength);

    void setSoftSelection(boolean softSelection);
}
