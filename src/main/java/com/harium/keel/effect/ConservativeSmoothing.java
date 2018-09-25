// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
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
 * Conservative Smoothing.
 * <p>
 * <p> Conservative smoothing is a noise reduction technique that derives its name from the fact that it employs a
 * simple, fast filtering algorithm that sacrifices noise suppression power in order to preserve the high
 * spatial frequency detail (e.g. sharp edges) in an image. It is explicitly designed to remove noise spikes --- i.e. isolated pixels of
 * exceptionally low or high pixel intensity (e.g. salt and pepper noise). </p>
 * <p>
 * <p><li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class ConservativeSmoothing implements Effect {

    private int radius = 1;

    /**
     * Get Radius.
     *
     * @return Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set Radius.
     *
     * @param radius Radius.
     */
    public void setRadius(int radius) {
        this.radius = Math.max(1, radius);
    }

    /**
     * Initialize a new instance of the ConservativeSmoothing class.
     */
    public ConservativeSmoothing() {
    }

    /**
     * Initialize a new instance of the ConservativeSmoothing class.
     *
     * @param radius Radius.
     */
    public ConservativeSmoothing(int radius) {
        setRadius(radius);
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {

        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();

        int Xline, Yline;
        int lines = calcLines(radius);

        if (fastBitmap.isGrayscale()) {
            OneBandSource copy = new OneBandSource(fastBitmap);
            int minG;
            int maxG;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    minG = 255;
                    maxG = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = y + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = x + (j - radius);
                            if (((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) && (i != j)) {

                                if (copy.getRGB(Xline, Yline) > maxG) {
                                    maxG = copy.getRGB(Xline, Yline);
                                }

                                if (copy.getRGB(Xline, Yline) < minG) {
                                    minG = copy.getRGB(Xline, Yline);
                                }

                            }
                        }
                    }

                    int g = copy.getRGB(y, x);

                    if (g > maxG) g = maxG;
                    if (g < minG) g = minG;

                    fastBitmap.setRGB(y, x, g);
                }
            }
        } else {
            MatrixSource copy = new MatrixSource(fastBitmap);

            int minR, minG, minB;
            int maxR, maxG, maxB;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    minR = minG = minB = 255;
                    maxR = maxG = maxB = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = y + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = x + (j - radius);
                            if (((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) && (i != j)) {

                                if (copy.getR(Xline, Yline) > maxR) {
                                    maxR = copy.getR(Xline, Yline);
                                }

                                if (copy.getG(Xline, Yline) > maxG) {
                                    maxG = copy.getG(Xline, Yline);
                                }

                                if (copy.getB(Xline, Yline) > maxB) {
                                    maxB = copy.getB(Xline, Yline);
                                }

                                if (copy.getR(Xline, Yline) < minR) {
                                    minR = copy.getR(Xline, Yline);
                                }

                                if (copy.getG(Xline, Yline) < minG) {
                                    minG = copy.getG(Xline, Yline);
                                }

                                if (copy.getB(Xline, Yline) < minB) {
                                    minB = copy.getB(Xline, Yline);
                                }

                            }
                        }
                    }

                    int r = copy.getR(y, x);
                    int g = copy.getG(y, x);
                    int b = copy.getB(y, x);

                    if (r > maxR) r = maxR;
                    if (g > maxG) g = maxG;
                    if (b > maxB) b = maxB;

                    if (r < minR) r = minR;
                    if (g < minG) g = minG;
                    if (b < minB) b = minB;

                    int rgb = ColorHelper.getRGB(r, g, b);

                    fastBitmap.setRGB(y, x, rgb);
                }
            }
        }
        return fastBitmap;
    }

    private int calcLines(int radius) {
        return radius * 2 + 1;
    }

}
