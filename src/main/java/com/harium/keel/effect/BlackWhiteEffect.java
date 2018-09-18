package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Black and white effect based on relative luminance
 * Reference: https://en.wikipedia.org/wiki/Relative_luminance
 */

public class BlackWhiteEffect implements Effect {

    @Override
    public ImageSource apply(ImageSource input) {
        int w = input.getWidth();
        int h = input.getHeight();

        int[][] output = new int[h][w];

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                int a = input.getA(i, j);
                int r = input.getR(i, j);
                int g = input.getG(i, j);
                int b = input.getB(i, j);

                // Maximum value would be 255
                int lum = (int) (.2126 * r + .7152 * g + .0722 * b);
                int rgb = ColorHelper.getARGB(lum, lum, lum, a);
                output[j][i] = rgb;
            }
        }

        MatrixSource source = new MatrixSource(output);
        return source;
    }
}
