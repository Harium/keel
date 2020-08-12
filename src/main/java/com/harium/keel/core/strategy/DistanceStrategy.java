package com.harium.keel.core.strategy;

public interface DistanceStrategy {
    /**
     * Method to calculate distance between rgb colors
     * @param rgb
     * @param otherRgb
     * @return distance value between 0 and 1
     */
    float distance(int rgb, int otherRgb);
}
