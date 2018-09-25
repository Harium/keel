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
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Subtract filter - subtract pixel values of one or two images.
 *
 * @author Diego Catalano
 */
public class Subtract implements Effect {

    private ImageSource overlayImage;
    private int red = 0, green = 0, blue = 0, gray = 0;
    private boolean isOverlay = false;

    /**
     * Initialize a new instance of the Subtract class.
     */
    public Subtract() {
    }

    /**
     * Initialize a new instance of the Subtract class.
     *
     * @param gray Gray value.
     */
    public Subtract(int gray) {
        this.gray = Math.abs(gray);
    }

    /**
     * Initialize a new instance of the Subtract class.
     *
     * @param r Red value.
     * @param g Green value.
     * @param b Blue value.
     */
    public Subtract(int r, int g, int b) {
        this.red = Math.abs(r);
        this.green = Math.abs(g);
        this.blue = Math.abs(b);
    }

    /**
     * Initialize a new instance of the Subtract class.
     *
     * @param overlayImage Overlay image.
     */
    public Subtract(ImageSource overlayImage) {
        this.overlayImage = overlayImage;
        this.isOverlay = true;
    }

    /**
     * Sets an overlay image, which will be used as the second image required to process source image.
     *
     * @param overlayImage Overlay image.
     */
    public void setOverlayImage(ImageSource overlayImage) {
        this.overlayImage = overlayImage;
        this.isOverlay = true;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        if (isOverlay) {
            return ApplyInPlaceImage(input);
        } else {
            return ApplyInPlaceValues(input);
        }
    }

    private ImageSource ApplyInPlaceValues(ImageSource sourceImage) {
        int size = EffectHelper.getSize(sourceImage);
        if (sourceImage.isGrayscale()) {
            int l;
            for (int i = 0; i < size; i++) {
                l = EffectHelper.getGray(i, sourceImage) - gray;
                l = ColorHelper.clamp(l);
                EffectHelper.setGray(i, l, sourceImage);
            }
        } else {
            int r, g, b;
            for (int i = 0; i < size; i++) {
                r = EffectHelper.getRed(i, sourceImage) - red;
                g = EffectHelper.getGreen(i, sourceImage) - green;
                b = EffectHelper.getBlue(i, sourceImage) - blue;

                r = ColorHelper.clamp(r);
                g = ColorHelper.clamp(g);
                b = ColorHelper.clamp(b);
                EffectHelper.setRGB(i, r, g, b, sourceImage);
            }
        }

        return sourceImage;
    }

    private ImageSource ApplyInPlaceImage(ImageSource sourceImage) {
        int size = EffectHelper.getSize(sourceImage);
        int sizeDestination = EffectHelper.getSize(overlayImage);

        if ((sourceImage.isGrayscale()) && (overlayImage.isGrayscale())) {
            if (size == sizeDestination) {
                int l;
                for (int i = 0; i < size; i++) {
                    l = EffectHelper.getGray(i, sourceImage) - EffectHelper.getGray(i, overlayImage);
                    l = l < 0 ? 0 : l;
                    EffectHelper.setGray(i, l, sourceImage);
                }
            }
        } else /*if ((sourceImage.isRGB()) && (overlayImage.isRGB()))*/ {
            if (size == sizeDestination) {
                int r, g, b;
                for (int i = 0; i < size; i++) {
                    r = EffectHelper.getRed(i, sourceImage) - EffectHelper.getRed(i, overlayImage);
                    g = EffectHelper.getGreen(i, sourceImage) - EffectHelper.getGreen(i, overlayImage);
                    b = EffectHelper.getBlue(i, sourceImage) - EffectHelper.getBlue(i, overlayImage);

                    r = ColorHelper.clamp(r);
                    g = ColorHelper.clamp(g);
                    b = ColorHelper.clamp(b);
                    EffectHelper.setRGB(i, r, g, b, sourceImage);
                }
            }
        }

        return sourceImage;
    }

}