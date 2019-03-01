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

package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;

/**
 * Dilatation operator from Mathematical Morphology.
 * The filter assigns maximum value of surrounding pixels to each pixel of the result image. Surrounding pixels, which should be processed, are specified by structuring element: 1 - to process the neighbor, 0 - to skip it.
 * The filter especially useful for binary image processing, where it allows to grow separate objects or join objects.
 * <p>
 * <p><li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.
 * <p>
 *
 * @author Diego Catalano
 */
public class Dilatation extends RadiusEffect implements Effect {

    private int[][] kernel;

    /**
     * Initialize a new instance of the Dilatation class.
     */
    public Dilatation() {
        super();
    }

    /**
     * Initialize a new instance of the Dilatation class.
     *
     * @param radius Radius.
     */
    public Dilatation(int radius) {
        super(radius);
    }

    /**
     * Initialize a new instance of the Dilatation class.
     *
     * @param kernel Kernel.
     */
    public Dilatation(int[][] kernel) {
        this.kernel = kernel;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int height = input.getHeight();
        int width = input.getWidth();

        if (kernel == null) {
            createKernel(radius);
        }

        if (input.isGrayscale()) {
            OneBandSource copy = new OneBandSource(input);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    int X = 0, Y;
                    int max = 0;
                    for (int x = i - radius; x < i + radius + 1; x++) {
                        Y = 0;
                        for (int y = j - radius; y < j + radius + 1; y++) {

                            if (x >= 0 && x < height && y >= 0 && y < width) {
                                int rgb = copy.getRGB(y, x);
                                int gray = ColorHelper.getRed(rgb);
                                int val = gray + kernel[X][Y];

                                if (val > max) {
                                    max = val;
                                }
                            }
                            Y++;
                        }
                        X++;
                    }

                    max = ColorHelper.clamp(max);
                    int rgb = ColorHelper.getRGB(max, max, max);
                    input.setRGB(j, i, rgb);
                }
            }
        } else {
            MatrixSource copy = new MatrixSource(input);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    int X = 0, Y;
                    int maxR = 0, maxG = 0, maxB = 0;
                    for (int x = i - radius; x < i + radius + 1; x++) {
                        Y = 0;
                        for (int y = j - radius; y < j + radius + 1; y++) {

                            if (x >= 0 && x < height && y >= 0 && y < width) {
                                int rgb = copy.getRGB(y, x);
                                int valR = ColorHelper.getRed(rgb) + kernel[X][Y];
                                int valG = ColorHelper.getGreen(rgb) + kernel[X][Y];
                                int valB = ColorHelper.getBlue(rgb) + kernel[X][Y];

                                if (valR > maxR) {
                                    maxR = valR;
                                }

                                if (valG > maxG) {
                                    maxG = valG;
                                }

                                if (valB > maxB) {
                                    maxB = valB;
                                }

                            }
                            Y++;
                        }
                        X++;
                    }

                    maxR = ColorHelper.clamp(maxR);
                    maxG = ColorHelper.clamp(maxG);
                    maxB = ColorHelper.clamp(maxB);

                    // Keep alpha?
                    int a = ColorHelper.getAlpha(input.getRGB(j, i));
                    int rgb = ColorHelper.getARGB(maxR, maxG, maxB, a);
                    input.setRGB(j, i, rgb);
                }
            }
        }

        return input;
    }

    private void createKernel(int radius) {
        int size = radius * 2 + 1;
        this.kernel = new int[size][size];
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                kernel[i][j] = 1;
            }
        }
    }

}