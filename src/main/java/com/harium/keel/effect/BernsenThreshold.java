// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
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

package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.OneBandSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Bernsen Threshold.
 * <p>
 * <p>The method uses a user-provided contrast threshold.
 * If the local contrast (max-min) is above or equal to the contrast threshold, the threshold is set
 * at the local midgrey value (the mean of the minimum and maximum grey values in the local window).
 * If the local contrast is below the contrast threshold the neighbourhood is considered to consist only of one class
 * and the pixel is set to object or background depending on the value of the midgrey.</p>
 * <p>
 * <p><b>Properties:</b>
 * <li>Supported types: Grayscale.
 * <br><li>Coordinate System: Matrix.</p>
 *
 * @author Diego Catalano
 */
public class BernsenThreshold implements Effect {

    private int radius = 15;
    private double c = 15;

    /**
     * Get radius.
     *
     * @return Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set radius.
     *
     * @param radius Radius.
     */
    public void setRadius(int radius) {
        this.radius = Math.max(1, radius);
    }

    /**
     * Get Contrast threshold.
     *
     * @return Contrast threshold.
     */
    public double getContrastThreshold() {
        return c;
    }

    /**
     * Set Contrast threshold.
     *
     * @param c Contrast threshold.
     */
    public void setContrastThreshold(double c) {
        this.c = Math.max(0, c);
    }

    /**
     * Initialize a new instance of the BernsenThreshold class.
     */
    public BernsenThreshold() {
    }

    /**
     * Initialize a new instance of the BernsenThreshold class.
     *
     * @param radius Radius.
     */
    public BernsenThreshold(int radius) {
        this.radius = radius;
    }

    /**
     * Initialize a new instance of the BernsenThreshold class.
     *
     * @param radius            Radius
     * @param contrastThreshold Contrast Threshold.
     */
    public BernsenThreshold(int radius, double contrastThreshold) {
        this.radius = radius;
        this.c = contrastThreshold;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        if (!input.isGrayscale()) {
            throw new IllegalArgumentException("Bernsen Threshold only works in grayscale images.");
        }

        OneBandSource max = OneBandSource.copy(input);
        OneBandSource min = OneBandSource.copy(input);

        Maximum maximum = new Maximum(radius);
        maximum.apply(max);

        Minimum minimum = new Minimum(radius);
        minimum.apply(min);

        int size = EffectHelper.getSize(input);

        for (int i = 0; i < size; i++) {
            double localContrast = EffectHelper.getGray(i, max) - EffectHelper.getGray(i, min);
            double midG = (EffectHelper.getGray(i, max) + EffectHelper.getGray(i, min)) / 2;

            int g = EffectHelper.getGray(i, input);
            if (localContrast < c)
                g = (midG >= 128) ? 255 : 0;
            else
                g = (g >= midG) ? 255 : 0;

            EffectHelper.setGray(i, g, input);
        }

        return input;
    }
}