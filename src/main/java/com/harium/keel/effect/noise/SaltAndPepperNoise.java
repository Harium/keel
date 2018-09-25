// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2007-2008
// andrew.kirillov@gmail.com
//
//    This library is free software; you can redistribute it and/or
//    modify it under the terms of the GNU Lesser General Public
//    License as published by the Free Software Foundation; either
//    version 2.1 of the License, or (at your option) any later version.
//
//    This library is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//    Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with this library; if not, write to the Free Software
//    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//

package com.harium.keel.effect.noise;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

import java.util.Random;

/**
 * Salt and pepper noise.
 * <br /> The filter adds random salt and pepper noise - sets maximum or minimum values to randomly selected pixels.
 *
 * @author Diego Catalano
 */
public class SaltAndPepperNoise implements Effect {

    private int noiseAmount = 10;
    private Random random = new Random();

    /**
     * Initializes a new instance of the OtsuThreshold class.
     */
    public SaltAndPepperNoise() {
        super();
    }

    /**
     * Initializes a new instance of the OtsuThreshold class.
     *
     * @param amount Amount of noise to generate in percents, [0, 100].
     */
    public SaltAndPepperNoise amount(int amount) {
        this.noiseAmount = Math.max(0, Math.min(100, amount));
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int width = input.getWidth();
        int height = input.getHeight();
        int noise = (width * height * noiseAmount) / 200;

        int[] lut = new int[]{0, 255};

        if (input.isGrayscale()) {
            for (int i = 0; i < noise; i++) {
                int y = random.nextInt(height);
                int x = random.nextInt(width);

                int color = random.nextInt(lut.length);

                int rgb = ColorHelper.getRGB(lut[color], lut[color], lut[color]);
                input.setRGB(x, y, rgb);
            }
        } else {
            for (int i = 0; i < noise; i++) {
                int y = random.nextInt(height);
                int x = random.nextInt(width);

                int band = random.nextInt(lut.length);
                int color = random.nextInt(lut.length);

                int original = input.getRGB(x, y);

                int r = ColorHelper.getRed(original);
                int g = ColorHelper.getGreen(original);
                int b = ColorHelper.getBlue(original);
                int a = ColorHelper.getAlpha(original);

                if (band == 0) {
                    r = lut[color];
                } else if (band == 1) {
                    g = lut[color];
                } else if (band == 2) {
                    b = lut[color];
                }

                int rgb = ColorHelper.getARGB(r, g, b, a);
                input.setRGB(x, y, rgb);
            }
        }

        return input;
    }

}