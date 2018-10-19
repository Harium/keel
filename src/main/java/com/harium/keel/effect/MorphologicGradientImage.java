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
import com.harium.keel.core.source.MatrixSource;

/**
 * Morphologic Gradient Image.
 * <p>
 * <para> In mathematical morphology and digital image processing, a morphological gradient is the
 * difference between the dilation and the erosion of a given image.
 * It is an image where each pixel value (typically non-negative) indicates the contrast intensity in the
 * close neighborhood of that pixel. It is useful for edge detection and segmentation applications.</para>
 * <p>
 * References: http://en.wikipedia.org/wiki/Morphological_gradient
 *
 * @author Diego Catalano
 */
public class MorphologicGradientImage implements Effect {

    private int[][] kernel;
    private int radius = 1;
    private boolean applyRadius = true;

    /**
     * Get Radius of morphologic operators.
     *
     * @return Radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set Radius of morphologic operators.
     *
     * @param radius Radius.
     */
    public void setRadius(int radius) {
        this.radius = radius;
        this.applyRadius = true;
    }

    /**
     * Get Kernel of morphologic operators.
     *
     * @return Kernel.
     */
    public int[][] getKernel() {
        return kernel;
    }

    /**
     * Set Kernel of morphologic operators.
     *
     * @param kernel Kernel.
     */
    public void setKernel(int[][] kernel) {
        this.kernel = kernel;
        this.applyRadius = false;
    }

    /**
     * Initialize a new instance of the MorphologicGradientImage class.
     */
    public MorphologicGradientImage() {
    }

    /**
     * Initialize a new instance of the MorphologicGradientImage class.
     *
     * @param radius Radius.
     */
    public MorphologicGradientImage(int radius) {
        this.radius = radius;
        this.applyRadius = true;
    }

    /**
     * Initialize a new instance of the MorphologicGradientImage class.
     *
     * @param kernel Kernel.
     */
    public MorphologicGradientImage(int[][] kernel) {
        this.kernel = kernel;
        this.applyRadius = false;
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        MatrixSource temp = new MatrixSource(fastBitmap);

        if (applyRadius) {
            Dilatation dil = new Dilatation(radius);
            dil.apply(fastBitmap);

            Erosion ero = new Erosion(radius);
            ero.apply(temp);
        } else {
            Dilatation dil = new Dilatation(kernel);
            dil.apply(fastBitmap);

            Erosion ero = new Erosion(kernel);
            ero.apply(temp);
        }

        Subtract sub = new Subtract(temp);
        sub.apply(fastBitmap);

        return fastBitmap;
    }
}