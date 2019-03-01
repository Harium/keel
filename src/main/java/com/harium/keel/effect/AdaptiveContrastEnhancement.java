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
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.OneBandSource;

/**
 * Adaptive Contrast Enhancement is modification of the gray level values based on some criterion that adjusts its parameters as local image characteristics change.
 * <p>
 * <p><b>Filter supports:</b>
 * <li>Images: Grayscale.
 * <br><li>Coordinate System: Matrix.</p>
 *
 * @author Diego Catalano
 */
public class AdaptiveContrastEnhancement implements Effect {

    int windowSize;
    double k1, k2, maxGain, minGain;

    /**
     * Initialize a new instance of the AdaptiveContrastEnhancement class.
     *
     * @param windowSize Size of window(should be an odd number).
     * @param k1         Local gain factor, between 0 and 1.
     * @param k2         Local mean constant, between 0 and 1.
     * @param minGain    The minimum gain factor.
     * @param maxGain    The maximum gain factor.
     */
    public AdaptiveContrastEnhancement(int windowSize, double k1, double k2, double minGain, double maxGain) {
        this.windowSize = windowSize;
        this.k1 = k1;
        this.k2 = k2;
        this.minGain = minGain;
        this.maxGain = maxGain;
    }

    public AdaptiveContrastEnhancement k1(double k1) {
        this.k1 = k1;
        return this;
    }

    public AdaptiveContrastEnhancement k2(double k2) {
        this.k2 = k2;
        return this;
    }

    public AdaptiveContrastEnhancement maxGain(double maxGain) {
        this.maxGain = maxGain;
        return this;
    }

    public AdaptiveContrastEnhancement minGain(double minGain) {
        this.minGain = minGain;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        if (!input.isGrayscale()) {
            throw new IllegalArgumentException("AdaptiveContrastEnhancement works only with grayscale.");
        }

        int width = input.getWidth();
        int height = input.getHeight();
        int lines = calcLines(windowSize);

        OneBandSource copy = OneBandSource.copy(input);

        // the mean (average) for the entire image I(x,y);
        double mean = getMean(input);

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {

                int hits = 0;
                int windowSize2 = windowSize * windowSize;
                int[] values = new int[windowSize2];

                double sumMean = 0;
                double sumVar = 0;
                double factor;

                for (int i = x - lines; i <= x + lines; i++) {
                    for (int j = y - lines; j <= y + lines; j++) {

                        if ((i >= 0) && (i < height) && (j >= 0) && (j < width)) {
                            // Get Gray
                            values[hits] = copy.getG(i, j);
                            //sumGray += values[hits];
                            sumMean += values[hits];
                            sumVar += values[hits] * values[hits];
                            hits++;
                        }
                    }
                }

                sumMean /= windowSize2;
                sumVar /= windowSize2;
                sumVar -= sumMean * sumMean;

                if (sumVar != 0)
                    factor = k1 * (mean / sumVar);
                else
                    factor = maxGain;

                if (factor > maxGain) factor = maxGain;
                if (factor < minGain) factor = minGain;

                double gray = factor * (copy.getRGB(y, x) - sumMean) + k2 * sumMean;
                input.setRGB(y, x, (int) gray);
            }
        }

        return input;
    }

    private double getMean(ImageSource input) {

        int sum = 0;
        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                sum += input.getG(i, j);
            }
        }

        return sum / (input.getWidth() * input.getHeight());
    }

    private int calcLines(int windowSize) {
        return (windowSize - 1) / 2;
    }

}