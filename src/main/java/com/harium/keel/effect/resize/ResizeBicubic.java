// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2005-2008
// andrew.kirillov@gmail.com
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

package com.harium.keel.effect.resize;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.interpolation.Interpolation;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Resize image using bicubic interpolation algorithm.
 * <p>
 * <para>The class implements image resizing filter using bicubic
 * interpolation algorithm. It uses bicubic kernel W(x) as described on
 * http://en.wikipedia.org/wiki/Bicubic_interpolation#Bicubic_convolution_algorithm
 * (coefficient <b>a</b> is set to <b>-0.5</b>).</para>
 *
 * @author Diego Catalano
 */
public class ResizeBicubic extends ResizeOperation {

    @Override
    public ImageSource apply(ImageSource fastBitmap) {

        MatrixSource output = new MatrixSource(width, height);

        int w = fastBitmap.getWidth();
        int h = fastBitmap.getHeight();
        double jFactor = (double) w / (double) width;
        double iFactor = (double) h / (double) height;

        // coordinates of source points
        double ox, oy, dx, dy, k1, k2;
        int ox1, oy1, ox2, oy2;

        // width and height decreased by 1
        int imax = height - 1;
        int jmax = width - 1;

        for (int i = 0; i < height; i++) {

            // Y coordinates
            oy = (double) i * iFactor - 0.5;
            oy1 = (int) oy;
            dy = oy - (double) oy1;

            for (int j = 0; j < width; j++) {

                // X coordinates
                ox = (double) j * jFactor - 0.5f;
                ox1 = (int) ox;
                dx = ox - (double) ox1;

                int r, g, b;
                r = g = b = 0;

                for (int n = -1; n < 3; n++) {

                    // get Y cooefficient
                    k1 = Interpolation.BiCubicKernel(dy - (double) n);

                    oy2 = oy1 + n;
                    if (oy2 < 0)
                        oy2 = 0;
                    if (oy2 > imax)
                        oy2 = imax;

                    for (int m = -1; m < 3; m++) {

                        // get X cooefficient
                        k2 = k1 * Interpolation.BiCubicKernel((double) m - dx);

                        ox2 = ox1 + m;
                        if (ox2 < 0)
                            ox2 = 0;
                        if (ox2 > jmax)
                            ox2 = jmax;

                        int rgb = fastBitmap.getRGB(ox2, oy2);

                        r += k2 * ColorHelper.getRed(rgb);
                        g += k2 * ColorHelper.getGreen(rgb);
                        b += k2 * ColorHelper.getBlue(rgb);
                    }
                }

                r = ColorHelper.clamp(r);
                g = ColorHelper.clamp(g);
                b = ColorHelper.clamp(b);

                int rgb = ColorHelper.getRGB(r, g, b);
                output.setRGB(j, i, rgb);
            }
        }
        return output;

    }

}