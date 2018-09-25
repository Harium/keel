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
 * Binary Opening operator from Mathematical Morphology.
 * <p> Applied to binary image, the filter may be used for removing small object keeping big objects unchanged. Since erosion is used first, it removes all small objects. Then dilatation restores big objects, which were not removed by erosion.</p>
 * <p>
 * <p><li>Supported types: Grayscale.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class BinaryOpening implements Effect {

    private int[][] kernel;
    private int radius = 0;

    /**
     * Initializes a new instance of the Binary Opening class.
     */
    public BinaryOpening() {
        this.radius = 1;
    }

    /**
     * Initializes a new instance of the Binary Opening class.
     *
     * @param se Structuring element.
     */
    public BinaryOpening(int[][] se) {
        this.kernel = se;
    }

    /**
     * Initializes a new instance of the Binary Opening class.
     *
     * @param radius Radius.
     */
    public BinaryOpening(int radius) {
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        if (!input.isGrayscale()) {
            throw new IllegalArgumentException("Binary Closing only works in grayscale images.");
        }

        if (radius != 0) {
            return apply(input, radius);
        } else {
            return apply(input, kernel);
        }
    }

    private ImageSource apply(ImageSource source, int[][] se) {
        BinaryDilatation dil = new BinaryDilatation(se);
        BinaryErosion ero = new BinaryErosion(se);
        ero.apply(source);
        dil.apply(source);

        return source;
    }

    private ImageSource apply(ImageSource source, int radius) {
        BinaryDilatation dil = new BinaryDilatation(radius);
        BinaryErosion ero = new BinaryErosion(radius);
        ero.apply(source);
        dil.apply(source);

        return source;
    }
}