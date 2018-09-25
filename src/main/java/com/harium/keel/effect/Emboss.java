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
import com.harium.keel.core.source.ImageSource;

/**
 * Emboss filter.
 * <p>
 * <p><li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class Emboss implements Effect {

    //Emboss Kernel
    int[][] kernel = {
            {-2, 0, 0},
            {0, 1, 0},
            {0, 0, 2}};

    /**
     * Initialize a new instance of the Emboss class.
     */
    public Emboss() {
    }

    /**
     * Apply filter to a FastBitmap.
     */
    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        Convolution c = new Convolution(kernel);
        return c.apply(fastBitmap);
    }
}
