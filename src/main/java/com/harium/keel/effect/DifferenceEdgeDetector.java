// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2007-2008
// andrew.kirillov@gmail.com
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
import com.harium.keel.core.source.OneBandSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Difference edge detector.
 * <p>
 * <p>The filter finds objects' edges by calculating maximum difference
 * between pixels in 4 directions around the processing pixel.</p>
 * <p>
 * <p>Suppose 3x3 square element of the source image (x - is currently processed pixel):
 * <code lang="none">
 * P1 P2 P3
 * P8  x P4
 * P7 P6 P5
 * </code>
 * The corresponding pixel of the result image equals to:
 * <code lang="none">
 * max( |P1-P5|, |P2-P6|, |P3-P7|, |P4-P8| )
 * </code>
 * </p>
 * <p>
 * <p><li>Supported types: Grayscale.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class DifferenceEdgeDetector implements Effect {

    /**
     * Initializes a new instance of the DifferenceEdgeDetector class.
     */
    public DifferenceEdgeDetector() {
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        if (!fastBitmap.isGrayscale()) {
            throw new IllegalArgumentException("DifferenceEdgeDetector only works in grayscale images.");
        }

        OneBandSource copy = new OneBandSource(fastBitmap);
        int width = copy.getWidth() - 2;
        int height = copy.getHeight() - 2;

        int stride = fastBitmap.getWidth();
        int offset = stride + 1;

        int max, diff;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {

                max = 0;

                diff = EffectHelper.getGray(offset - stride - 1, copy) - EffectHelper.getGray(offset + stride + 1, copy);
                if (diff < 0) diff = -diff;
                if (diff > max) max = diff;

                diff = EffectHelper.getGray(offset - stride, copy) - EffectHelper.getGray(offset + stride, copy);
                if (diff < 0) diff = -diff;
                if (diff > max) max = diff;

                diff = EffectHelper.getGray(offset - stride + 1, copy) - EffectHelper.getGray(offset + stride - 1, copy);
                if (diff < 0) diff = -diff;
                if (diff > max) max = diff;

                diff = EffectHelper.getGray(offset + 1, copy) - EffectHelper.getGray(offset - 1, copy);
                if (diff < 0) diff = -diff;
                if (diff > max) max = diff;

                EffectHelper.setGray(offset, max, fastBitmap);
                offset++;
            }
            offset += 2;
        }
        return fastBitmap;
    }

}