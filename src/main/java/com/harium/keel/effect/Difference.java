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
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Difference filter - get the difference between overlay and source images.
 * <p> The difference filter takes two images (source and overlay images) of the same size and pixel format and produces an image, where each pixel equals to absolute difference between corresponding pixels from provided images.</p>
 * <p>
 * <p><li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Matrix.
 *
 * @author Diego Catalano
 */
public class Difference implements Effect {

    private ImageSource overlayImage;

    /**
     * Initialize a new instance of the Difference class.
     */
    public Difference() {
    }

    /**
     * Initialize a new instance of the Difference class.
     *
     * @param overlayImage Overlay image.
     */
    public Difference(ImageSource overlayImage) {
        this.overlayImage = overlayImage;
    }

    /**
     * Sets an overlay image, which will be used as the second image required to process source image. See documentation of particular inherited class for information about overlay image purpose.
     *
     * @param overlayImage Overlay image.
     */
    public void setOverlayImage(ImageSource overlayImage) {
        this.overlayImage = overlayImage;
    }

    @Override
    public ImageSource apply(ImageSource sourceImage) {

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int sizeOrigin = width * height;
        int sizeDestination = overlayImage.getWidth() * overlayImage.getHeight();
        if ((sourceImage.isGrayscale()) && (overlayImage.isGrayscale())) {
            if (sizeOrigin == sizeDestination) {

                int l;
                for (int x = 0; x < height; x++) {
                    for (int y = 0; y < width; y++) {
                        l = sourceImage.getRGB(x, y) - overlayImage.getRGB(x, y);
                        l = l < 0 ? -l : l;
                        sourceImage.setRGB(x, y, l);
                    }
                }
            }
        } else {
            if (sizeOrigin == sizeDestination) {
                int r, g, b;
                for (int x = 0; x < height; x++) {
                    for (int y = 0; y < width; y++) {
                        r = sourceImage.getR(x, y) - overlayImage.getR(x, y);
                        g = sourceImage.getG(x, y) - overlayImage.getG(x, y);
                        b = sourceImage.getB(x, y) - overlayImage.getB(x, y);

                        r = r < 0 ? -r : r;
                        g = g < 0 ? -g : g;
                        b = b < 0 ? -b : b;

                        EffectHelper.setRGB(x, y, r, g, b, sourceImage);
                    }
                }
            }
        }
        return sourceImage;
    }
}
