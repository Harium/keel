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
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Resize image using bilinear interpolation algorithm.
 * <p>
 * <para>The class implements image resizing filter using bilinear
 * interpolation algorithm.</para>
 *
 * @author Diego Catalano
 */
public class ResizeBilinear extends ResizeOperation {

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource output = new MatrixSource(width, height);

        int w = input.getWidth();
        int h = input.getHeight();
        double jFactor = (double) w / (double) width;
        double iFactor = (double) h / (double) height;

        // coordinates of source points
        double ox, oy, dx1, dy1, dx2, dy2;
        int ox1, oy1, ox2, oy2;
        // width and height decreased by 1
        int imax = height - 1;
        int jmax = width - 1;
        // temporary values
        int tp1, tp2;
        int p1, p2, p3, p4;

        for (int i = 0; i < height; i++) {

            // Y coordinates
            oy = (double) i * iFactor;
            oy1 = (int) oy;
            oy2 = (oy1 == imax) ? oy1 : oy1 + 1;
            dy1 = oy - (double) oy1;
            dy2 = 1.0 - dy1;

            // get temp pointers
            tp1 = oy1;
            tp2 = oy2;

            for (int j = 0; j < width; j++) {
                // X coordinates
                ox = (double) j * jFactor;
                ox1 = (int) ox;
                ox2 = (ox1 == jmax) ? ox1 : ox1 + 1;
                dx1 = ox - (double) ox1;
                dx2 = 1.0 - dx1;

                int rgb1 = input.getRGB(ox1, tp1);
                int rgb2 = input.getRGB(ox2, tp1);
                int rgb3 = input.getRGB(ox1, tp2);
                int rgb4 = input.getRGB(ox2, tp2);

                // get four points in red channel
                p1 = ColorHelper.getRed(rgb1);
                p2 = ColorHelper.getRed(rgb2);
                p3 = ColorHelper.getRed(rgb3);
                p4 = ColorHelper.getRed(rgb4);

                int r = (int) (
                        dy2 * (dx2 * (p1) + dx1 * (p2)) +
                                dy1 * (dx2 * (p3) + dx1 * (p4)));

                // get four points in green channel
                p1 = ColorHelper.getGreen(rgb1);
                p2 = ColorHelper.getGreen(rgb2);
                p3 = ColorHelper.getGreen(rgb3);
                p4 = ColorHelper.getGreen(rgb4);

                int g = (int) (
                        dy2 * (dx2 * (p1) + dx1 * (p2)) +
                                dy1 * (dx2 * (p3) + dx1 * (p4)));

                // get four points in blue channel
                p1 = ColorHelper.getBlue(rgb1);
                p2 = ColorHelper.getBlue(rgb2);
                p3 = ColorHelper.getBlue(rgb3);
                p4 = ColorHelper.getBlue(rgb4);

                int b = (int) (
                        dy2 * (dx2 * (p1) + dx1 * (p2)) +
                                dy1 * (dx2 * (p3) + dx1 * (p4)));


                int rgb = ColorHelper.getRGB(r, g, b);
                output.setRGB(j, i, rgb);
            }
        }
        return output;
    }

}