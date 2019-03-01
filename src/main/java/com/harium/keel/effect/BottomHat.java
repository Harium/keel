// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2007-2008
// andrew.kirillov at gmail.com
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

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.OneBandSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Bottom-hat operator from Mathematical Morphology.
 * <br /> Bottom-hat morphological operator subtracts input image from the result of morphological closing on the the input image.
 * <br /> Applied to binary image, the filter allows to get all object parts, which were added by closing filter, but were not removed after that due to formed connections/fillings.
 * <p>
 * <p><li>Supported types: Grayscale.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class BottomHat implements Effect {
    private int[][] kernel;
    private int radius;


    /**
     * Initialize a new instance of the BottomHat class.
     */
    public BottomHat() {
        this.radius = 1;
    }

    /**
     * Initialize a new instance of the BottomHat class.
     *
     * @param se Structuring element.
     */
    public BottomHat(int[][] se) {
        this.kernel = se;
    }

    /**
     * Initialize a new instance of the BottomHat class.
     *
     * @param radius Radius.
     */
    public BottomHat(int radius) {
        radius = radius < 1 ? 1 : radius;
        this.radius = radius;
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        if (!fastBitmap.isGrayscale()) {
            throw new IllegalArgumentException("Bottom Hat only works in grayscale images.");
        }

        if (radius != 0) {
            return apply(fastBitmap, radius);
        } else {
            return apply(fastBitmap, kernel);
        }
    }

    private ImageSource apply(ImageSource source, int radius) {

        OneBandSource l = new OneBandSource(source);

        BinaryClosing close = new BinaryClosing(radius);
        close.apply(l);

        Subtract sub = new Subtract(source);
        sub.apply(l);

        EffectHelper.setImage(l, source);
        return source;
    }

    private ImageSource apply(ImageSource source, int[][] se) {

        OneBandSource l = OneBandSource.copy(source);

        BinaryClosing close = new BinaryClosing(se);
        close.apply(l);

        Subtract sub = new Subtract(source);
        sub.apply(l);

        EffectHelper.setImage(l, source);
        return source;
    }

}