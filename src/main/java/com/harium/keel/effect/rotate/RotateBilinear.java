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

package com.harium.keel.effect.rotate;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Rotate image using bilinear algorithm.
 * <p>
 * <para>The class implements image rotation filter using bilinear
 * algorithm, which does not assume any interpolation.</para>
 * <p>
 * <para><note>Rotation is performed in counterclockwise direction.</note></para>
 *
 * @author Diego Catalano
 */
public class RotateBilinear extends RotateOperation {

    public RotateBilinear() {
        super();
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();
        double oldIradius = (double) (height - 1) / 2;
        double oldJradius = (double) (width - 1) / 2;

        newWidth = calculateWidth(width, height);
        newHeight = calculateHeight(width, height);
        MatrixSource destinationData = new MatrixSource(newWidth, newHeight);

        // get destination image size
        double newIradius = (double) (newHeight - 1) / 2;
        double newJradius = (double) (newWidth - 1) / 2;

        // destination pixel's coordinate relative to image center
        double ci, cj;

        // coordinates of source points
        double oi, oj, ti, tj, di1, dj1, di2, dj2;
        int oi1, oj1, oi2, oj2;

        // width and height decreased by 1
        int imax = height - 1;
        int jmax = width - 1;

        ci = -newIradius;
        for (int i = 0; i < newHeight; i++) {

            // do some pre-calculations of source points' coordinates
            // (calculate the part which depends on y-loop, but does not
            // depend on x-loop)
            ti = angleSin * ci + oldIradius;
            tj = angleCos * ci + oldJradius;

            cj = -newJradius;
            for (int j = 0; j < newWidth; j++) {

                // coordinates of source point
                oi = ti + angleCos * cj;
                oj = tj - angleSin * cj;

                // top-left coordinate
                oi1 = (int) oi;
                oj1 = (int) oj;

                // validate source pixel's coordinates
                if ((oi1 < 0) || (oj1 < 0) || (oi1 >= height) || (oj1 >= width)) {
                    // fill destination image with filler
                    int rgb = ColorHelper.getARGB(fillRed, fillGreen, fillBlue, fillAlpha);
                    destinationData.setRGB(j, i, rgb);
                } else {
                    // bottom-right coordinate
                    oi2 = (oi1 == imax) ? oi1 : oi1 + 1;
                    oj2 = (oj1 == jmax) ? oj1 : oj1 + 1;

                    if ((di1 = oi - (double) oi1) < 0)
                        di1 = 0;
                    di2 = 1.0 - di1;

                    if ((dj1 = oj - (double) oj1) < 0)
                        dj1 = 0;
                    dj2 = 1.0 - dj1;

                    // get four points (red)
                    int rgb1 = fastBitmap.getRGB(oj1, oi1);
                    int rgb2 = fastBitmap.getRGB(oj2, oi1);
                    int rgb3 = fastBitmap.getRGB(oj1, oi2);
                    int rgb4 = fastBitmap.getRGB(oj2, oi2);

                    int p1 = ColorHelper.getRed(rgb1);
                    int p2 = ColorHelper.getRed(rgb2);
                    int p3 = ColorHelper.getRed(rgb3);
                    int p4 = ColorHelper.getRed(rgb4);

                    int r = (int) (
                            di2 * (dj2 * (p1) + dj1 * (p2)) +
                                    di1 * (dj2 * (p3) + dj1 * (p4)));

                    // get four points (green)
                    p1 = ColorHelper.getGreen(rgb1);
                    p2 = ColorHelper.getGreen(rgb2);
                    p3 = ColorHelper.getGreen(rgb3);
                    p4 = ColorHelper.getGreen(rgb4);

                    int g = (int) (
                            di2 * (dj2 * (p1) + dj1 * (p2)) +
                                    di1 * (dj2 * (p3) + dj1 * (p4)));

                    // get four points (blue)
                    p1 = ColorHelper.getBlue(rgb1);
                    p2 = ColorHelper.getBlue(rgb2);
                    p3 = ColorHelper.getBlue(rgb3);
                    p4 = ColorHelper.getBlue(rgb4);

                    int b = (int) (
                            di2 * (dj2 * (p1) + dj1 * (p2)) +
                                    di1 * (dj2 * (p3) + dj1 * (p4)));


                    int rgb = ColorHelper.getRGB(r, g, b);
                    destinationData.setRGB(j, i, rgb);
                }
                cj++;
            }
            ci++;
        }
        return destinationData;
    }


}