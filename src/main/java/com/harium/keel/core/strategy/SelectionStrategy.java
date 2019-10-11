package com.harium.keel.core.strategy;


public interface SelectionStrategy {
    boolean valid(int rgb, int j, int i);

    void setBaseRGB(int baseRGB);
    void setStrength(float strength);
}
