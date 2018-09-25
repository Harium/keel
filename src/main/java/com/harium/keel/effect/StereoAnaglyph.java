// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
// Copyright © Andrew Kirillov, 2005-2009
// andrew.kirillov@aforgenet.com
//
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
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

/**
 * Stereo anaglyph filter.
 * <para>The image processing filter produces stereo anaglyph images which are
 * aimed to be viewed through anaglyph glasses with red filter over the left eye and
 * cyan over the right.</para>
 *
 * @author Diego Catalano
 */
public class StereoAnaglyph implements Effect {

    private ImageSource overlayImage;
    private Algorithm algorithm;

    /**
     * Initializes a new instance of the StereoAnaglyph class.
     */
    public StereoAnaglyph() {
        super();
    }

    /**
     * Enumeration of algorithms for creating anaglyph images.
     */
    public enum Algorithm {

        /**
         * Creates anaglyph image using the below calculations:
         * <br />
         * <br /> Ra = 0.299*RI + 0.587 * GI + 0.114*BI;
         * <br /> Ga = 0;
         * <br /> Ba = 0.299 * Rr + 0.587 * Gr + 0.114 * Br.
         */
        TrueAnaglyph,
        /**
         * Creates anaglyph image using the below calculations:
         * <br />
         * <br /> Ra = 0.299*RI + 0.587 * GI + 0.114*BI;
         * <br /> Ga = 0.299*Rr + 0.587 * Gr + 0.114*Br;
         * <br /> Ba =0.299*Rr + 0.587 * Gr + 0.114*Br;
         */
        GrayAnaglyph,
        /**
         * Creates anaglyph image using the below calculations:
         * <br />
         * <br /> Ra = RI;
         * <br /> Ga = Gr;
         * <br /> Ba = Br;
         */
        ColorAnaglyph,
        /**
         * Creates anaglyph image using the below calculations:
         * <br />
         * <br /> Ra = 0.299*RI + 0.587 * GI + 0.114*BI;
         * <br /> Ga = Gr;
         * <br /> Ba = Br;
         */
        HalfColorAnaglyph,
        /**
         * Creates anaglyph image using the below calculations:
         * <br />
         * <br /> Ra = 0.7 * GI + 0.3 * BI;
         * <br /> Ga = Gr;
         * <br /> Ba = Br;
         */
        OptimizedAnaglyph
    }

    public StereoAnaglyph overlay(ImageSource overlayImage) {
        this.overlayImage = overlayImage;
        return this;
    }

    /**
     * Algorithm for creating anaglyph images.
     *
     * @return Algorithm.
     */
    public Algorithm algorithm() {
        return algorithm;
    }

    /**
     * Algorithm for creating anaglyph images.
     *
     * @param algorithm Algorithm.
     */
    public StereoAnaglyph algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource sourceImage) {

        switch (algorithm) {
            case TrueAnaglyph:
                applyTrueAnaglyph(sourceImage);
                break;

            case GrayAnaglyph:
                applyGrayAnaglyph(sourceImage);
                break;

            case ColorAnaglyph:
                applyColorAnaglyph(sourceImage);
                break;

            case HalfColorAnaglyph:
                applyHalfColorAnaglyph(sourceImage);
                break;

            case OptimizedAnaglyph:
                applyOptimizedAnaglyph(sourceImage);
                break;
        }

        return sourceImage;
    }

    private void applyTrueAnaglyph(ImageSource sourceImage) {
        for (int i = 0; i < sourceImage.getHeight(); i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                int sourceRGB = sourceImage.getRGB(j, i);
                int overlayRGB = overlayImage.getRGB(j, i);

                int r = (int) (ColorHelper.getRed(sourceRGB) * 0.299 + ColorHelper.getGreen(sourceRGB) * 0.587 + ColorHelper.getBlue(sourceRGB) * 0.114);
                int g = 0;
                int b = (int) (ColorHelper.getRed(overlayRGB) * 0.299 + ColorHelper.getGreen(overlayRGB) * 0.587 + ColorHelper.getBlue(overlayRGB) * 0.114);

                int rgb = ColorHelper.getRGB(r, g, b);

                sourceImage.setRGB(j, i, rgb);
            }
        }
    }

    private void applyGrayAnaglyph(ImageSource sourceImage) {
        for (int i = 0; i < sourceImage.getHeight(); i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                int sourceRGB = sourceImage.getRGB(j, i);
                int overlayRGB = overlayImage.getRGB(j, i);

                int r = (int) (ColorHelper.getRed(sourceRGB) * 0.299 + ColorHelper.getGreen(sourceRGB) * 0.587 + ColorHelper.getBlue(sourceRGB) * 0.114);
                int g = (int) (ColorHelper.getRed(overlayRGB) * 0.299 + ColorHelper.getGreen(overlayRGB) * 0.587 + ColorHelper.getBlue(overlayRGB) * 0.114);
                int b = g;

                int rgb = ColorHelper.getRGB(r, g, b);

                sourceImage.setRGB(j, i, rgb);
            }
        }
    }

    private void applyColorAnaglyph(ImageSource sourceImage) {
        for (int i = 0; i < sourceImage.getHeight(); i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                int sourceRGB = sourceImage.getRGB(j, i);
                int overlayRGB = overlayImage.getRGB(j, i);

                // Red from source
                int r = ColorHelper.getRed(sourceRGB);
                // Green and blue from overlay
                int g = ColorHelper.getGreen(overlayRGB);
                int b = ColorHelper.getBlue(overlayRGB);

                int rgb = ColorHelper.getRGB(r, g, b);

                sourceImage.setRGB(j, i, rgb);
            }
        }
    }

    private void applyHalfColorAnaglyph(ImageSource sourceImage) {
        for (int i = 0; i < sourceImage.getHeight(); i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                int sourceRGB = sourceImage.getRGB(j, i);
                int overlayRGB = overlayImage.getRGB(j, i);

                int r = (int) (ColorHelper.getRed(sourceRGB) * 0.299 + ColorHelper.getGreen(sourceRGB) * 0.587 + ColorHelper.getBlue(sourceRGB) * 0.114);
                int g = ColorHelper.getGreen(overlayRGB);
                int b = ColorHelper.getBlue(overlayRGB);

                int rgb = ColorHelper.getRGB(r, g, b);

                sourceImage.setRGB(j, i, rgb);
            }
        }
    }

    private void applyOptimizedAnaglyph(ImageSource sourceImage) {
        for (int i = 0; i < sourceImage.getHeight(); i++) {
            for (int j = 0; j < sourceImage.getWidth(); j++) {
                int sourceRGB = sourceImage.getRGB(j, i);
                int overlayRGB = overlayImage.getRGB(j, i);

                int r = (int) (ColorHelper.getGreen(sourceRGB) * 0.7 + ColorHelper.getBlue(sourceRGB) * 0.3);
                int g = ColorHelper.getGreen(overlayRGB);
                int b = ColorHelper.getBlue(overlayRGB);

                int rgb = ColorHelper.getRGB(r, g, b);

                sourceImage.setRGB(j, i, rgb);
            }
        }
    }

}
