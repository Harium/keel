package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

/**
 * Brightness / Contrast Effect
 * <p>
 * Code from: https://docs.opencv.org/3.4.2/d3/dc1/tutorial_basic_linear_transform.html
 */
public class BrightnessContrast implements Effect {

    // Alpha value
    private float gain = 0;
    // Beta value
    private int bias = 0;

    public BrightnessContrast() {
        super();
    }

    @Override
    public ImageSource apply(ImageSource input) {
        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {

                int rgb = input.getRGB(j, i);

                int alpha = ColorHelper.getAlpha(rgb);
                int red = ColorHelper.getRed(rgb);
                int green = ColorHelper.getGreen(rgb);
                int blue = ColorHelper.getBlue(rgb);

                int nr = ColorHelper.clamp((int) (gain * red + bias));
                int ng = ColorHelper.clamp((int) (gain * green + bias));
                int nb = ColorHelper.clamp((int) (gain * blue + bias));

                input.setRGB(j, i, ColorHelper.getARGB(nr, ng, nb, alpha));
            }
        }

        return input;
    }

    public BrightnessContrast gain(float gain) {
        this.gain = gain;
        return this;
    }

    public BrightnessContrast bias(int bias) {
        this.bias = bias;
        return this;
    }
}
