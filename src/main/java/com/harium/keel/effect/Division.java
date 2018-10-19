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

/**
 * Division filter - divide pixel values of one or two images.
 * <p>
 * <p><li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class Division implements Effect {

    private ImageSource overlayImage;
    private double red = 0, green = 0, blue = 0, gray = 0;
    private boolean isOverlay = false;

    /**
     * Initialize a new instance of the Division class.
     */
    public Division() {
    }

    /**
     * Initialize a new instance of the Division class.
     *
     * @param gray Gray value.
     */
    public Division(double gray) {
        this.gray = gray;
    }

    /**
     * Initialize a new instance of the Division class.
     *
     * @param r Red value.
     * @param g Green value.
     * @param b Blue value.
     */
    public Division(double r, double g, double b) {
        this.red = Math.abs(r);
        this.green = Math.abs(g);
        this.blue = Math.abs(b);
    }

    /**
     * Initialize a new instance of the Division class.
     *
     * @param overlayImage Overlay image.
     */
    public Division(ImageSource overlayImage) {
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
    public ImageSource apply(ImageSource sourceImage) {
        if (isOverlay) {
            applyImage(sourceImage);
        } else {
            applyValues(sourceImage);
        }

        return sourceImage;
    }

    /**
     * @param sourceImage
     */
    public void applyValues(ImageSource sourceImage) {

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        if (sourceImage.isGrayscale()) {
            int l;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    l = (int) ((double) sourceImage.getGray(x, y) / gray);
                    l = ColorHelper.clamp(l);
                    sourceImage.setGray(x, y, l);
                }
            }
        } else {

            int r, g, b, a;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    r = (int) ((double) sourceImage.getR(x, y) / red);
                    g = (int) ((double) sourceImage.getG(x, y) / green);
                    b = (int) ((double) sourceImage.getB(x, y) / blue);
                    a = sourceImage.getA(x, y);

                    r = ColorHelper.clamp(r);
                    g = ColorHelper.clamp(g);
                    b = ColorHelper.clamp(b);

                    int rgb = ColorHelper.getARGB(r, g, b, a);
                    sourceImage.setRGB(x, y, rgb);
                }
            }
        }
    }

    /**
     * @param sourceImage
     */
    public void applyImage(ImageSource sourceImage) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int sizeOrigin = width * height;
        int sizeDestination = overlayImage.getWidth() * overlayImage.getHeight();
        if ((sourceImage.isGrayscale()) && (overlayImage.isGrayscale())) {
            if (sizeOrigin == sizeDestination) {
                int l;
                for (int x = 0; x < height; x++) {
                    for (int y = 0; y < width; y++) {
                        l = sourceImage.getRGB(y, x) / overlayImage.getRGB(y, x);
                        l = ColorHelper.clamp(l);
                        sourceImage.setRGB(y, x, l);
                    }
                }
            }
        } else {
            if (sizeOrigin == sizeDestination) {

                int r, g, b, a;
                for (int x = 0; x < height; x++) {
                    for (int y = 0; y < width; y++) {
                        r = sourceImage.getR(y, x) / overlayImage.getR(y, x);
                        g = sourceImage.getG(y, x) / overlayImage.getG(y, x);
                        b = sourceImage.getB(y, x) / overlayImage.getB(y, x);
                        a = sourceImage.getA(y, x);

                        r = ColorHelper.clamp(r);
                        g = ColorHelper.clamp(g);
                        b = ColorHelper.clamp(b);

                        int rgb = ColorHelper.getARGB(r, g, b, a);
                        sourceImage.setRGB(y, x, rgb);
                    }
                }
            }
        }
    }
}
