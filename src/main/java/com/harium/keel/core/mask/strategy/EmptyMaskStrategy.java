package com.harium.keel.core.mask.strategy;

import com.harium.keel.core.mask.DynamicPixel;
import com.harium.keel.core.source.ImageSource;

public class EmptyMaskStrategy implements DynamicMaskStrategy {

    public void reset(int[][] mask) {
        initMask(mask);
    }

    @Override
    public void update(ImageSource source, int[][] mask) {
        reset(mask);
    }

    public static void updateMask(int[][] mask, int i, int j, int w, int h, int status) {
        for (int nj = j; nj < j + h; nj++) {
            for (int ni = i; ni < i + w; ni++) {
                mask[ni][nj] = status;
            }
        }
    }

    static void initMask(int[][] mask) {
        int w = mask.length;
        int h = mask[0].length;

        updateMask(mask, 0, 0, w, h, DynamicPixel.UNKNOWN);
    }

}
