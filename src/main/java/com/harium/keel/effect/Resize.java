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

import com.harium.keel.core.Effect;
import com.harium.keel.core.interpolation.Algorithm;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.resize.ResizeBicubic;
import com.harium.keel.effect.resize.ResizeBilinear;
import com.harium.keel.effect.resize.ResizeNearestNeighbor;
import com.harium.keel.effect.resize.ResizeOperation;

/**
 * Resize image using interpolation algorithms.
 *
 * @author Diego Catalano
 */
public class Resize implements Effect {

    private int width = 0, height = 0;
    private Algorithm algorithm = Algorithm.NEAREST_NEIGHBOR;

    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        ResizeOperation operation;

        switch (algorithm) {
            case BILINEAR:
                operation = new ResizeBilinear();
                break;
            case BICUBIC:
                operation = new ResizeBicubic();
                break;
            case NEAREST_NEIGHBOR:
            default:
                operation = new ResizeNearestNeighbor();
        }

        // Proportional Scale
        if (width == 0 && height > 0) {
            float scale = fastBitmap.getHeight() / (float) height;
            width = (int) (fastBitmap.getWidth() * scale);
        }

        if (height == 0 && width > 0) {
            float scale = fastBitmap.getWidth() / (float) width;
            height = (int) (fastBitmap.getHeight() * scale);
        }

        operation.size(width, height);
        return operation.apply(fastBitmap);
    }

    public Resize using(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public Resize width(int width) {
        this.width = width;
        return this;
    }

    public Resize height(int height) {
        this.height = height;
        return this;
    }

    public Resize size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }


}