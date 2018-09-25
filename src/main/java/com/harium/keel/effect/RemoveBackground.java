package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

/**
 * Black and white effect based on relative luminance
 * Reference: https://en.wikipedia.org/wiki/Relative_luminance
 */

public class RemoveBackground implements Effect {

    int r, g, b;

    public RemoveBackground color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                int r = input.getR(j, i);
                int g = input.getG(j, i);
                int b = input.getB(j, i);

                // Ignore alpha
                if (this.r == r && this.g == g && this.b == b) {
                    int rgb = ColorHelper.getARGB(0, 0, 0, 0);
                    input.setRGB(j, i, rgb);
                }
            }
        }

        return input;
    }
}
