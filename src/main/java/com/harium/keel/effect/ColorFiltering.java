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
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Color filtering.
 * <p> The filter filters pixels inside/outside of specified RGB color range - it keeps pixels with colors inside/outside of specified range and fills the rest with specified color</p>.
 * <p>
 * <p><li>Supported types: RGB.
 * <br><li>Coordinate System: Independent.
 *
 * @author Diego Catalano
 */
public class ColorFiltering implements Effect {

    private int minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue;

    public int getMinRed() {
        return minRed;
    }

    public void setMinRed(int minRed) {
        this.minRed = minRed;
    }

    public int getMaxRed() {
        return maxRed;
    }

    public void setMaxRed(int maxRed) {
        this.maxRed = maxRed;
    }

    public int getMinGreen() {
        return minGreen;
    }

    public void setMinGreen(int minGreen) {
        this.minGreen = minGreen;
    }

    public int getMaxGreen() {
        return maxGreen;
    }

    public void setMaxGreen(int maxGreen) {
        this.maxGreen = maxGreen;
    }

    public int getMinBlue() {
        return minBlue;
    }

    public void setMinBlue(int minBlue) {
        this.minBlue = minBlue;
    }

    public int getMaxBlue() {
        return maxBlue;
    }

    public void setMaxBlue(int maxBlue) {
        this.maxBlue = maxBlue;
    }

    /**
     * Initialize a new instance of the ColorFiltering class.
     */
    public ColorFiltering() {
    }

    public ColorFiltering(int minRed, int maxRed, int minGreen, int maxGreen, int minBlue, int maxBlue) {
        this.minRed = minRed;
        this.maxRed = maxRed;
        this.minGreen = minGreen;
        this.maxGreen = maxGreen;
        this.minBlue = minBlue;
        this.maxBlue = maxBlue;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        if (input.isGrayscale()) {
            throw new IllegalArgumentException("Color filtering only works in RGB images.");
        }

        int r, g, b, a;
        int size = EffectHelper.getSize(input);

        for (int i = 0; i < size; i++) {
            r = EffectHelper.getRed(i, input);
            g = EffectHelper.getGreen(i, input);
            b = EffectHelper.getBlue(i, input);
            a = EffectHelper.getAlpha(i, input);

            if ((r >= minRed) && (r <= maxRed) &&
                    (g >= minGreen) && (g <= maxGreen) &&
                    (b >= minBlue) && (b <= maxBlue)) {
                EffectHelper.setRGB(i, r, g, b, input);
            } else {
                EffectHelper.setRGB(i, 0, 0, 0, a, input);
            }
        }

        return input;
    }

}