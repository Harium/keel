package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Gamma correction algorithm
 * <p/>
 * Author: Bostjan Cigan (http://zerocool.is-a-geek.net)
 * Original code: https://bostjan-cigan.com/java-gamma-correction-algorithm/
 */
public class GammaCorrectionEffect implements Effect {

    private float gamma = 0;

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource output = new MatrixSource(input);

        float gammaValue = 1 / gamma;
        int[] gammaLUT = gammaLUT(gammaValue);

        for (int j = 0; j < input.getHeight(); j++) {
            for (int i = 0; i < input.getWidth(); i++) {

                int rgb = input.getRGB(i, j);

                int alpha = ColorHelper.getAlpha(rgb);
                int red = ColorHelper.getRed(rgb);
                int green = ColorHelper.getGreen(rgb);
                int blue = ColorHelper.getBlue(rgb);

                red = gammaLUT[red];
                green = gammaLUT[green];
                blue = gammaLUT[blue];

                // Return back to original format
                int np = ColorHelper.getARGB(red, green, blue, alpha);

                // Write pixels into image
                output.setRGB(i, j, np);
            }
        }

        return output;
    }

    // Create the gamma correction lookup table
    private static int[] gammaLUT(double gammaValue) {
        int[] gammaLUT = new int[256];

        for (int i = 0; i < gammaLUT.length; i++) {
            gammaLUT[i] = (int) (255 * (Math.pow((double) i / (double) 255, gammaValue)));
        }

        return gammaLUT;
    }

    public GammaCorrectionEffect gamma(float gamma) {
        this.gamma = gamma;
        return this;
    }

}