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
import com.harium.keel.core.source.OneBandSource;

/**
 * Remove artifacts caused by uneven lightning.
 * <p>Actually this technique is useful as a preprocessing step for all texture measures.</p>
 * <br>Reference: Computer Imaging: digital image analysis and processing / Scott E. Umbaugh. Chapter 6. p. 276
 * <p>
 * <p><b>Properties:</b>
 * <li>Supported types: Grayscale.
 * <br><li>Coordinate System: Matrix.</p>
 *
 * @author Diego Catalano
 */
public class ArtifactsRemoval implements Effect {

    private int windowSize = 15;

    /**
     * Initialize a new instance of the ArtifactsRemoval class.
     */
    public ArtifactsRemoval() {
    }

    /**
     * Initialize a new instance of the ArtifactsRemoval class.
     *
     * @param windowSize Window size.
     */
    public ArtifactsRemoval(int windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {
        if (!fastBitmap.isGrayscale()) {
            throw new IllegalArgumentException("Artifacts Removal only works in grayscale images.");
        }

        OneBandSource copy = OneBandSource.copy(fastBitmap);

        int width = fastBitmap.getWidth();
        int height = fastBitmap.getHeight();
        int steps = windowSize / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double sum = 0;
                int hits = 0;
                for (int i = y - steps; i < y + steps; i++) {
                    for (int j = x - steps; j < x + steps; j++) {

                        if (((i >= 0) && (j >= 0)) && ((i < height) && (j < width))) {
                            sum += copy.getRGB(j, i);
                            hits++;
                        }
                    }
                }
                double mean = sum / hits;
                fastBitmap.setRGB(x, y, (int) (fastBitmap.getRGB(x, y) - mean));
            }
        }

        return fastBitmap;
    }
}