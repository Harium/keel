// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2007-2008
// andrew.kirillov at gmail.com
//
//    This library is free software; you can redistribute it and/or
//    apply it under the terms of the GNU Lesser General Public
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
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Additive noise filter.
 * <p>
 * <p>The filter adds random value to each pixel of the source image.</p>
 * <p>
 * <p><b>Properties:</b>
 * <li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.</p>
 *
 * @author Diego Catalano
 */
public class AdditiveNoise implements Effect {

    private int min = -10;
    private int max = 10;

    /**
     * Get minimum value.
     *
     * @return Minimum value.
     */
    public int getMin() {
        return min;
    }

    /**
     * Set minimum value.
     *
     * @param min Minimum value.
     */
    public AdditiveNoise min(int min) {
        this.min = min;
        return this;
    }

    /**
     * Get maximum value.
     *
     * @return Maximum value.
     */
    public int getMax() {
        return max;
    }

    /**
     * Set maximum value.
     *
     * @param max Maximum value.
     */
    public AdditiveNoise max(int max) {
        this.max = max;
        return this;
    }

    /**
     * Initialize a new instance of the AdditiveNoise class.
     *
     * @param min Min value.
     * @param max Max value.
     */
    public AdditiveNoise range(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {

        int height = input.getHeight();
        int width = input.getWidth();

        if (input.isGrayscale()) {
            int size = width * height;

            for (int i = 0; i < size; i++) {
                int gray = EffectHelper.getRGB(i, input);
                gray = ColorHelper.clamp(gray + generateNumber());
                EffectHelper.setRGB(i, gray, input);
            }
        } else {

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int r = input.getR(x, y);
                    int g = input.getG(x, y);
                    int b = input.getB(x, y);
                    int a = input.getA(x, y);

                    r = ColorHelper.clamp(r + generateNumber());
                    g = ColorHelper.clamp(g + generateNumber());
                    b = ColorHelper.clamp(b + generateNumber());

                    int rgb = ColorHelper.getARGB(r, g, b, a);
                    input.setRGB(x, y, rgb);
                }
            }
        }

        return input;
    }

    private int generateNumber() {
        return Math.min(min, max) + (int) Math.round(-0.5f + (1 + Math.abs(min - max)) * Math.random());
    }

}