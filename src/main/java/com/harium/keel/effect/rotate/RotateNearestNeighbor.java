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
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Rotate image using nearest neighbor algorithm.
 * <p>
 * <para>The class implements image rotation filter using nearest
 * neighbor algorithm, which does not assume any interpolation.</para>
 * <p>
 * <para><note>Rotation is performed in counterclockwise direction.</note></para>
 *
 * @author Diego Catalano
 */
public class RotateNearestNeighbor extends RotateOperation {

    public RotateNearestNeighbor() {
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

        // source pixel's coordinates
        int oi, oj;

        ci = -newIradius;
        for (int i = 0; i < newHeight; i++) {
            cj = -newJradius;
            for (int j = 0; j < newWidth; j++) {

                // coordinate of the nearest point
                oi = (int) (angleCos * ci + angleSin * cj + oldIradius);
                oj = (int) (-angleSin * ci + angleCos * cj + oldJradius);

                // validate source pixel's coordinates
                if ((oi < 0) || (oj < 0) || (oi >= height) || (oj >= width)) {
                    // fill destination image with filler
                    int rgb = ColorHelper.getARGB(fillRed, fillGreen, fillBlue, fillAlpha);
                    destinationData.setRGB(j, i, rgb);
                } else {
                    int rgb = fastBitmap.getRGB(oj, oi);
                    destinationData.setRGB(j, i, rgb);
                }
                cj++;
            }
            ci++;
        }

        return destinationData;
    }
}