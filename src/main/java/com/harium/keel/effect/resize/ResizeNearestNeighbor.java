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

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Resize image using nearest neighbor algorithm.
 * <p>
 * <para>The class implements image resizing filter using nearest
 * neighbor algorithm, which does not assume any interpolation.</para>
 *
 * @author Diego Catalano
 */
public class ResizeNearestNeighbor extends ResizeOperation {

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource output = new MatrixSource(width, height);

        int w = input.getWidth();
        int h = input.getHeight();
        double jFactor = (double) w / (double) width;
        double iFactor = (double) h / (double) height;
        for (int i = 0; i < height; i++) {
            int I = (int) (i * iFactor);

            for (int j = 0; j < width; j++) {
                int J = (int) (j * jFactor);

                int rgb = input.getRGB(J, I);

                output.setRGB(j, i, rgb);
            }
        }

        return output;
    }

}