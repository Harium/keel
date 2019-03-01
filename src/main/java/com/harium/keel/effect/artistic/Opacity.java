// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
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

package com.harium.keel.effect.artistic;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

/**
 * Opacity filter.
 *
 * @author Diego Catalano
 */
public class Opacity implements Effect {

    private ImageSource overlay;
    private double p = 0.65D;

    /**
     * Get overlay image.
     *
     * @return Overlay image.
     */
    public ImageSource overlay() {
        return overlay;
    }

    /**
     * Set overlay image.
     *
     * @param overlay Overlay image.
     */
    public Opacity overlay(ImageSource overlay) {
        this.overlay = overlay;
        return this;
    }

    /**
     * Get percentage.
     *
     * @return Percentage.
     */
    public double getPercentage() {
        return p;
    }

    /**
     * Set percetange.
     *
     * @param p Percentage [0..1].
     */
    public Opacity setPercentage(double p) {
        this.p = Math.max(0, Math.min(1, p));
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {

        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                double a1 = input.getA(j, i);
                double r1 = input.getR(j, i);
                double g1 = input.getG(j, i);
                double b1 = input.getB(j, i);

                double a2 = overlay.getA(j, i);
                double r2 = overlay.getR(j, i);
                double g2 = overlay.getG(j, i);
                double b2 = overlay.getB(j, i);

                double a = (1 - p) * a1 + p * a2;
                double r = (1 - p) * r1 + p * r2;
                double g = (1 - p) * g1 + p * g2;
                double b = (1 - p) * b1 + p * b2;

                int rgb = ColorHelper.getARGB((int) r, (int) g, (int) b, (int) a);

                input.setRGB(j, i, rgb);
            }
        }
        return input;
    }
}