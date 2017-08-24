package com.harium.keel.core.mask.strategy;


import com.harium.keel.core.source.ImageSource;

public interface DynamicMaskStrategy {
    void reset(int[][] mask);

    void update(ImageSource source, int[][] mask);
}
