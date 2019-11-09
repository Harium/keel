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
package com.harium.keel.filter.process;

import com.harium.keel.catalano.core.IntPoint;
import com.harium.keel.core.Processor;
import com.harium.keel.core.source.ImageSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Extract Boundary using approach with morphology operators.
 *
 * @author Diego Catalano
 */
public class ExtractBoundaryProcessor implements Processor<List<IntPoint>> {

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

    /**
     * Get points after extract boundary.
     *
     * @param fastBitmap Image to be processed.
     * @return List of points.
     */
    public List<IntPoint> apply(ImageSource fastBitmap) {
        List<IntPoint> points = new ArrayList<>();

        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();

        if (fastBitmap.isGrayscale()) {
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    if (fastBitmap.getRGB(y, x) == 255) points.add(new IntPoint(y, x));
                }
            }
        } else {
            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    // TODO Check for green and blue?
                    if (fastBitmap.getR(y, x) == 255) points.add(new IntPoint(y, x));
                }
            }
        }

        return points;
    }

}