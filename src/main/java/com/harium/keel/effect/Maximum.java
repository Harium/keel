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
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;

/**
 * Maximum filter.
 * <br /> Maximum filter - set maximum pixel values using radius.
 *
 * @author Diego Catalano
 */
public class Maximum implements Effect {

    private int radius = 1;

    /**
     * Initialize a new instance of the Maximum class.
     */
    public Maximum() {
    }

    /**
     * Initialize a new instance of the Maximum class.
     *
     * @param radius Radius.
     */
    public Maximum(int radius) {
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
    }

    /**
     * Radius.
     *
     * @return Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Radius.
     *
     * @param radius Radius.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int width = input.getWidth();
        int height = input.getHeight();

        int Xline, Yline;
        int lines = calcLines(radius);

        if (input.isGrayscale()) {
            OneBandSource copy = OneBandSource.copy(input);
            int maxG;
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    maxG = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) {
                                maxG = Math.max(maxG, copy.getRGB(Xline, Yline));
                            }
                        }
                    }
                    input.setRGB(y, x, maxG);
                }
            }
        } else {
            MatrixSource copy = new MatrixSource(input);
            int maxR, maxG, maxB;
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    maxR = maxG = maxB = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) {
                                maxR = Math.max(maxR, copy.getR(Xline, Yline));
                                maxG = Math.max(maxG, copy.getG(Xline, Yline));
                                maxB = Math.max(maxB, copy.getB(Xline, Yline));
                            }
                        }
                    }
                    int rgb = ColorHelper.getRGB(maxR, maxG, maxB);
                    input.setRGB(y, x, rgb);
                }
            }
        }

        return input;
    }

    private int calcLines(int radius) {
        return radius * 2 + 1;
    }
}