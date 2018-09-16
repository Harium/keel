package com.harium.keel.core.effect;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Brightness / Contrast Effect
 *
 * Code from: https://docs.opencv.org/3.4.2/d3/dc1/tutorial_basic_linear_transform.html
 */
public class BrightnessContrastEffect implements Effect {

    // Alpha value
    private float gain = 0;
    // Beta value
    private int bias = 0;

    public BrightnessContrastEffect() {
        super();
    }

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource out = new MatrixSource(input);

        for (int j = 0; j < input.getHeight(); j++) {
            for (int i = 0; i < input.getWidth(); i++) {

                int rgb = input.getRGB(i, j);

                int alpha = ColorHelper.getAlpha(rgb);
                int red = ColorHelper.getRed(rgb);
                int green = ColorHelper.getGreen(rgb);
                int blue = ColorHelper.getBlue(rgb);

                int nr = ColorHelper.clamp((int) (gain * red + bias));
                int ng = ColorHelper.clamp((int) (gain * green + bias));
                int nb = ColorHelper.clamp((int) (gain * blue + bias));

                out.setRGB(i, j, ColorHelper.getARGB(nr, ng, nb, alpha));
            }
        }

        return out;
    }

    public BrightnessContrastEffect gain(float gain) {
        this.gain = gain;
        return this;
    }

    public BrightnessContrastEffect bias(int bias) {
        this.bias = bias;
        return this;
    }
}
