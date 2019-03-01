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

package com.harium.keel.effect.rotate;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.interpolation.Interpolation;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Rotate image using bicubic algorithm.
 * <p>
 * <para>The class implements image rotation filter using bicubic
 * algorithm, which does not assume any interpolation.</para>
 * <p>
 * <para><note>Rotation is performed in counterclockwise direction.</note></para>
 * <p>
 *
 * @author Diego Catalano
 */
public class RotateBicubic extends RotateOperation {

    public RotateBicubic() {
        super();
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int width = input.getWidth();
        int height = input.getHeight();
        double oldIradius = (double) (height - 1) / 2;
        double oldJradius = (double) (width - 1) / 2;

        newWidth = calculateWidth(width, height);
        newHeight = calculateHeight(width, height);
        MatrixSource output = new MatrixSource(newWidth, newHeight);

        // get destination image size
        double newIradius = (double) (newHeight - 1) / 2;
        double newJradius = (double) (newWidth - 1) / 2;

        // destination pixel's coordinate relative to image center
        double ci, cj;

        // coordinates of source points and cooefficiens
        double oi, oj, di, dj, k1, k2;
        int oi1, oj1, oi2, oj2;

        // width and height decreased by 1
        int imax = height - 1;
        int jmax = width - 1;

        ci = -newIradius;
        for (int i = 0; i < newHeight; i++) {
            cj = -newJradius;
            for (int j = 0; j < newWidth; j++) {

                // coordinate of the nearest point
                oi = angleCos * ci + angleSin * cj + oldIradius;
                oj = -angleSin * ci + angleCos * cj + oldJradius;

                oi1 = (int) oi;
                oj1 = (int) oj;

                // validate source pixel's coordinates
                if ((oi < 0) || (oj < 0) || (oi >= height) || (oj >= width)) {
                    // fill destination image with filler
                    int rgb = ColorHelper.getARGB(fillRed, fillGreen, fillBlue, fillAlpha);
                    output.setRGB(j, i, rgb);
                } else {
                    di = oi - (double) oi1;
                    dj = oj - (double) oj1;

                    // initial pixel value
                    int r = 0;
                    int g = 0;
                    int b = 0;

                    for (int n = -1; n < 3; n++) {
                        // get Y coefficient
                        k1 = Interpolation.BiCubicKernel(dj - (double) n);

                        oj2 = oj1 + n;
                        if (oj2 < 0)
                            oj2 = 0;
                        if (oj2 > jmax)
                            oj2 = jmax;

                        for (int m = -1; m < 3; m++) {
                            // get X coefficient
                            k2 = k1 * Interpolation.BiCubicKernel((double) m - di);

                            oi2 = oi1 + m;
                            if (oi2 < 0)
                                oi2 = 0;
                            if (oi2 > imax)
                                oi2 = imax;

                            int rgb = input.getRGB(oj2, oi2);

                            r += k2 * ColorHelper.getRed(rgb);
                            g += k2 * ColorHelper.getGreen(rgb);
                            b += k2 * ColorHelper.getBlue(rgb);
                        }
                    }

                    r = ColorHelper.clamp(r);
                    g = ColorHelper.clamp(g);
                    b = ColorHelper.clamp(b);

                    int rgb = ColorHelper.getRGB(r, g, b);
                    output.setRGB(i, j, rgb);
                }
                cj++;
            }
            ci++;
        }

        return output;
    }

}