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
 * Binary Erosion operator from Mathematical Morphology.
 * The filter assigns minimum value of surrounding pixels to each pixel of the result image. Surrounding pixels, which should be processed, are specified by structuring element: 1 - to process the neighbor, 0 - to skip it.
 * The filter especially useful for binary image processing, where it removes pixels, which are not surrounded by specified amount of neighbors. It gives ability to remove noisy pixels (stand-alone pixels) or shrink objects.
 * <p>
 * <p><li>Supported types: Grayscale.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class BinaryErosion extends RadiusEffect implements Effect {
    private int[][] kernel;
    private static final int BLACK = ColorHelper.getRGB(0, 0, 0);

    /**
     * Initialize a new instance of the Binary Erosion class with radius = 1.
     */
    public BinaryErosion() {
        this(1);
    }

    /**
     * Initialize a new instance of the Binary Erosion class with defined.
     *
     * @param radius Radius
     */
    public BinaryErosion(int radius) {
        super(radius);
    }

    /**
     * Initialize a new instance of the Binary Erosion class with structuring element.
     *
     * @param se Structuring element.
     */
    public BinaryErosion(int[][] se) {
        this.kernel = se;
    }

    /**
     * Apply filter to an image.
     *
     * @param source FastBitmap
     */
    @Override
    public ImageSource apply(ImageSource source) {
        if (radius != 0) {
            if (source.isGrayscale()) {
                return applyGrayscale(source, radius);
            } else {
                return applyRGB(source, radius);
            }
        } else {
            if (source.isGrayscale()) {
                return applyGrayscale(source, kernel);
            } else {
                return applyRGB(source, kernel);
            }
        }
    }

    private ImageSource applyGrayscale(ImageSource source, int radius) {
        OneBandSource copy = OneBandSource.copy(source);

        int width = source.getWidth();
        int height = source.getHeight();
        int l;

        int Xline, Yline;
        int lines = calcLines(radius);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                l = copy.getRGB(y, x);
                if (l == 0) {
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) {
                                source.setRGB(Yline, Xline, 0);
                            }
                        }
                    }
                }
            }
        }
        return source;
    }

    private ImageSource applyRGB(ImageSource source, int radius) {
        MatrixSource copy = new MatrixSource(source);

        int width = source.getWidth();
        int height = source.getHeight();
        int l;

        int Xline, Yline;
        int lines = calcLines(radius);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                l = copy.getB(y, x);
                if (l == 0) {
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) {

                                source.setRGB(Yline, Xline, BLACK);
                            }
                        }
                    }
                }
            }
        }
        return source;
    }

    private ImageSource applyGrayscale(ImageSource source, int[][] kernel) {
        OneBandSource copy = OneBandSource.copy(source);

        int width = source.getWidth();
        int height = source.getHeight();
        int l;

        int Xline, Yline;
        int lines = calcLines(kernel);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                l = copy.getRGB(y, x);
                if (l == 0) {
                    for (int i = 0; i < kernel.length; i++) {
                        Xline = x + (i - lines);
                        for (int j = 0; j < kernel[0].length; j++) {
                            Yline = y + (j - lines);
                            if ((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) {
                                if (kernel[i][j] == 1) {
                                    source.setRGB(Yline, Xline, 0);
                                }
                            }
                        }
                    }
                }
            }
        }

        return source;
    }

    private ImageSource applyRGB(ImageSource source, int[][] kernel) {
        MatrixSource copy = new MatrixSource(source);

        int width = source.getWidth();
        int height = source.getHeight();
        int l;

        int Xline, Yline;
        int lines = calcLines(kernel);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                l = copy.getB(y, x);
                if (l == 0) {
                    for (int i = 0; i < kernel.length; i++) {
                        Xline = x + (i - lines);
                        for (int j = 0; j < kernel[0].length; j++) {
                            Yline = y + (j - lines);
                            if ((Xline >= 0) && (Xline < height) && (Yline >= 0) && (Yline < width)) {
                                if (kernel[i][j] == 1) {
                                    source.setRGB(Yline, Xline, BLACK);
                                }
                            }
                        }
                    }
                }
            }
        }

        return source;
    }

    private int calcLines(int[][] se) {
        int lines = (se[0].length - 1) / 2;
        return lines;
    }

}