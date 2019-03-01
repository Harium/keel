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
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Add filter - add pixel values of one or two images.
 * <p>
 * <p><b>Properties:</b>
 * <li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.</p>
 *
 * @author Diego Catalano
 */
public class Add implements Effect {

    private ImageSource overlayImage;
    private int red = 0, green = 0, blue = 0, gray = 0;
    private boolean isOverlay = false;

    /**
     * Initialize a new instance of the Add class.
     *
     * @param gray Gray value.
     */
    public Add(int gray) {
        this.gray = gray;
    }

    /**
     * Initialize a new instance of the Add class.
     *
     * @param r Red value.
     * @param g Green value.
     * @param b Blue value.
     */
    public Add(int r, int g, int b) {
        this.red = Math.abs(r);
        this.green = Math.abs(g);
        this.blue = Math.abs(b);
    }

    /**
     * Initialize a new instance of the Add class.
     *
     * @param overlayImage Overlay image.
     */
    public Add(ImageSource overlayImage) {
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
            applyImage(input);
        } else {
            applyValues(input);
        }
        return input;
    }

    private void applyValues(ImageSource input) {

        int size = input.getWidth() * input.getHeight();

        if (input.isGrayscale()) {
            int l;
            for (int i = 0; i < size; i++) {
                l = EffectHelper.getRGB(i, input) + gray;
                l = ColorHelper.clamp(l);
                EffectHelper.setRGB(i, l, input);
            }
        } else {
            int r, g, b, a;
            for (int i = 0; i < size; i++) {
                r = EffectHelper.getRed(i, input) + red;
                g = EffectHelper.getGreen(i, input) + green;
                b = EffectHelper.getBlue(i, input) + blue;
                a = EffectHelper.getAlpha(i, input);

                r = ColorHelper.clamp(r);
                g = ColorHelper.clamp(g);
                b = ColorHelper.clamp(b);
                EffectHelper.setRGB(i, r, g, b, a, input);
            }
        }
    }

    private void applyImage(ImageSource input) {

        int size = input.getWidth() * input.getHeight();
        int sizeDestination = overlayImage.getWidth() * overlayImage.getHeight();
        if ((input.isGrayscale()) && (overlayImage.isGrayscale())) {
            if (size == sizeDestination) {
                int l;
                for (int i = 0; i < size; i++) {
                    l = EffectHelper.getRGB(i, input) + EffectHelper.getRGB(i, overlayImage);
                    l = ColorHelper.clamp(l);
                    EffectHelper.setRGB(i, l, input);
                }
            }
        } else {
            if (size == sizeDestination) {

                int r, g, b, a;
                for (int i = 0; i < size; i++) {

                    r = EffectHelper.getRed(i, input) + EffectHelper.getRed(i, overlayImage);
                    g = EffectHelper.getGreen(i, input) + EffectHelper.getGreen(i, overlayImage);
                    b = EffectHelper.getBlue(i, input) + EffectHelper.getBlue(i, overlayImage);
                    a = EffectHelper.getAlpha(i, input);

                    r = ColorHelper.clamp(r);
                    g = ColorHelper.clamp(g);
                    b = ColorHelper.clamp(b);
                    EffectHelper.setRGB(i, r, g, b, a, input);
                }
            }
        }
    }
}