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

package com.harium.keel.catalano.math.tools;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Gather statistics about image in Gray or RGB color space.
 *
 * @author Diego Catalano
 */
public class ImageStatistics {

    private ImageHistogram gray;
    private ImageHistogram red;
    private ImageHistogram green;
    private ImageHistogram blue;

    private int pixels;

    /**
     * Image histogram of gray channel.
     *
     * @return Histogram.
     */
    public ImageHistogram getHistogramGray() {
        if (gray == null)
            throw new IllegalArgumentException("Histogram gray is null");

        return gray;
    }

    /**
     * Image histogram of red channel.
     *
     * @return Histogram.
     */
    public ImageHistogram getHistogramRed() {
        if (red == null)
            throw new IllegalArgumentException("Histogram red is null");

        return red;
    }

    /**
     * Image histogram of green channel.
     *
     * @return Histogram.
     */
    public ImageHistogram getHistogramGreen() {
        if (green == null)
            throw new IllegalArgumentException("Histogram green is null");

        return green;
    }

    /**
     * Image histogram of blue channel.
     *
     * @return Histogram.
     */
    public ImageHistogram getHistogramBlue() {
        if (blue == null)
            throw new IllegalArgumentException("Histogram blue is null");

        return blue;
    }

    /**
     * Initialize a new instance of the ImageStatistics class.
     *
     * @param fastBitmap Image to be processed.
     */
    public ImageStatistics(ImageSource fastBitmap) {
        this(fastBitmap, 256);
    }

    /**
     * Initialize a new instance of the ImageStatistics class.
     *
     * @param fastBitmap Image to be processed.
     * @param bins       Number of bins.
     */
    public ImageStatistics(ImageSource fastBitmap, int bins) {

        pixels = 0;
        red = green = blue = gray = null;

        float factor = bins / 256f;
        int size = EffectHelper.getSize(fastBitmap);

        if (fastBitmap.isGrayscale()) {
            int[] g = new int[bins];

            int G;

            for (int i = 0; i < size; i++) {
                G = EffectHelper.getGray(i, fastBitmap);

                g[(int) (G * factor)]++;
                pixels++;
            }

            gray = new ImageHistogram(g);

        } else {
            int[] r = new int[bins];
            int[] g = new int[bins];
            int[] b = new int[bins];

            int R, G, B;

            for (int i = 0; i < size; i++) {
                R = EffectHelper.getRed(i, fastBitmap);
                G = EffectHelper.getGreen(i, fastBitmap);
                B = EffectHelper.getBlue(i, fastBitmap);

                r[(int) (R * factor)]++;
                g[(int) (G * factor)]++;
                b[(int) (B * factor)]++;
                pixels++;
            }
            red = new ImageHistogram(r);
            green = new ImageHistogram(g);
            blue = new ImageHistogram(b);
        }
    }

    /**
     * Count pixels.
     *
     * @return amount of pixels.
     */
    public int PixelsCount() {
        return pixels;
    }

    /**
     * Calculate Mean value.
     *
     * @param fastBitmap Image to be processed.
     * @return Mean.
     */
    public static float Mean(ImageSource fastBitmap) {
        return Mean(fastBitmap, 0, 0, fastBitmap.getWidth(), fastBitmap.getHeight());
    }

    /**
     * Calculate Mean value.
     *
     * @param fastBitmap Image to be processed.
     * @param startX     Initial X axis coordinate.
     * @param startY     Initial Y axis coordinate.
     * @param width      Width.
     * @param height     Height.
     * @return Mean.
     */
    public static float Mean(ImageSource fastBitmap, int startX, int startY, int width, int height) {
        float mean = 0;
        if (fastBitmap.isGrayscale()) {
            for (int i = startX; i < height; i++) {
                for (int j = startY; j < width; j++) {
                    mean += fastBitmap.getRGB(j, i);
                }
            }
            return mean / (width * height);
        } else {
            throw new IllegalArgumentException("ImageStatistics: Only compute mean in grayscale images.");
        }
    }

    /**
     * Calculate Variance.
     *
     * @param fastBitmap Image to be processed.
     * @return Variance.
     */
    public static float Variance(ImageSource fastBitmap) {
        float mean = Mean(fastBitmap);
        return Variance(fastBitmap, mean);
    }

    /**
     * Calculate Variance.
     *
     * @param fastBitmap Image to be processed.
     * @param mean       Mean.
     * @return Variance.
     */
    public static float Variance(ImageSource fastBitmap, float mean) {
        return Variance(fastBitmap, mean, 0, 0, fastBitmap.getWidth(), fastBitmap.getHeight());
    }

    /**
     * Calculate Variance.
     *
     * @param fastBitmap Image to be processed.
     * @param mean       Mean.
     * @param startX     Initial X axis coordinate.
     * @param startY     Initial Y axis coordinate.
     * @param width      Width.
     * @param height     Height.
     * @return Variance.
     */
    public static float Variance(ImageSource fastBitmap, float mean, int startX, int startY, int width, int height) {

        float sum = 0;
        if (fastBitmap.isGrayscale()) {
            for (int i = startX; i < height; i++) {
                for (int j = startY; j < width; j++) {
                    sum += Math.pow(fastBitmap.getRGB(j, i) - mean, 2);
                }
            }
            return sum / (float) ((width * height) - 1);
        } else {
            throw new IllegalArgumentException("ImageStatistics: Only compute variance in grayscale images.");
        }
    }

    /**
     * Get maximum gray value in the image.
     *
     * @param fastBitmap Image to be processed.
     * @return Maximum gray.
     */
    public static int Maximum(ImageSource fastBitmap) {
        return Maximum(fastBitmap, 0, 0, fastBitmap.getWidth(), fastBitmap.getHeight());
    }

    /**
     * Get maximum gray value in the image.
     *
     * @param fastBitmap Image to be processed.
     * @param startX     Initial X axis coordinate.
     * @param startY     Initial Y axis coordinate.
     * @param width      Width.
     * @param height     Height.
     * @return Maximum gray.
     */
    public static int Maximum(ImageSource fastBitmap, int startX, int startY, int width, int height) {
        int max = 0;

        if (fastBitmap.isGrayscale()) {
            for (int i = startX; i < height; i++) {
                for (int j = startY; j < width; j++) {
                    int gray = fastBitmap.getRGB(j, i);
                    if (gray > max) {
                        max = gray;
                    }
                }
            }
        } else {
            for (int i = startX; i < height; i++) {
                for (int j = startY; j < width; j++) {
                    int gray = fastBitmap.getG(j, i);
                    if (gray > max) {
                        max = gray;
                    }
                }
            }
        }

        return max;
    }

    /**
     * Get minimum gray value in the image.
     *
     * @param fastBitmap Image to be processed.
     * @return minimum gray.
     */
    public static int Minimum(ImageSource fastBitmap) {
        return Minimum(fastBitmap, 0, 0, fastBitmap.getWidth(), fastBitmap.getHeight());
    }

    /**
     * Get minimum gray value in the image.
     *
     * @param fastBitmap Image to be processed.
     * @param startX     Initial X axis coordinate.
     * @param startY     Initial Y axis coordinate.
     * @param width      Width.
     * @param height     Height.
     * @return Minimum gray.
     */
    public static int Minimum(ImageSource fastBitmap, int startX, int startY, int width, int height) {
        int min = 255;
        if (fastBitmap.isGrayscale()) {
            for (int i = startX; i < height; i++) {
                for (int j = startY; j < width; j++) {
                    int gray = fastBitmap.getRGB(j, i);
                    if (gray < min) {
                        min = gray;
                    }
                }
            }
        } else {
            for (int i = startX; i < height; i++) {
                for (int j = startY; j < width; j++) {
                    int gray = fastBitmap.getG(j, i);
                    if (gray < min) {
                        min = gray;
                    }
                }
            }
        }
        return min;
    }
}