// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
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

package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

/**
 * Desaturation.
 * <p>
 * <p><li>Supported types: RGB.
 * <br><li>Coordinate System: Independent.
 *
 * @author Diego Catalano
 */
public class Desaturation implements Effect {

    private double saturationFactor = 0.2D;

    /**
     * Get Saturation factor.
     *
     * @return Saturation factor.
     */
    public double saturationFactor() {
        return saturationFactor;
    }

    /**
     * Set Saturation factor.
     *
     * @param saturationFactor Saturation factor.
     * @return Desaturation
     */
    public Desaturation saturationFactor(double saturationFactor) {
        this.saturationFactor = Math.min(1, Math.max(0, saturationFactor));
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        if (input.isGrayscale()) {
            return input;
        }

        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                int a = input.getA(j, i);
                int r = input.getR(j, i);
                int g = input.getG(j, i);
                int b = input.getB(j, i);

                int luminance = (int) (.2125 * r + .7154 * g + .0721 * b);
                double nr = (luminance + saturationFactor * (r - luminance));
                double ng = (luminance + saturationFactor * (g - luminance));
                double nb = (luminance + saturationFactor * (b - luminance));

                int rgb = ColorHelper.getARGB((int) nr, (int) ng, (int) nb, a);
                input.setRGB(i, j, rgb);
            }
        }

        return input;
    }
}