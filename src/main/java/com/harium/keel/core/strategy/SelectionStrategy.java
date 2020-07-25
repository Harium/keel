package com.harium.keel.core.strategy;


public interface SelectionStrategy {
    boolean valid(int rgb, int x, int y);
}
