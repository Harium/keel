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

import com.harium.keel.catalano.core.IntPoint;
import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;

import java.util.ArrayList;

/**
 * Extract Boundary using approach with morphology operators.
 *
 * @author Diego Catalano
 */
public class ExtractBoundaryEffect implements Effect {

    /**
     * Morphology operators.
     */
    public enum Algorithm {

        /**
         * Erosion.
         */
        Erosion,
        /**
         * Dilatation.
         */
        Dilatation
    }

    ;
    private Algorithm algorithm = Algorithm.Erosion;
    private ArrayList<IntPoint> points;

    /**
     * Initialize a new instance of the ExtractBoundary class.
     */
    public ExtractBoundaryEffect() {
    }

    /**
     * Initialize a new instance of the ExtractBoundary class.
     *
     * @param algorithm Morphology algotithm.
     */
    public ExtractBoundaryEffect(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {

        if (algorithm == Algorithm.Erosion) {
            BinaryErosion ero = new BinaryErosion();
            ero.apply(fastBitmap);
        } else {
            BinaryDilatation dil = new BinaryDilatation();
            dil.apply(fastBitmap);
        }

        Difference dif = new Difference(fastBitmap);
        dif.apply(fastBitmap);

        return fastBitmap;
    }

}