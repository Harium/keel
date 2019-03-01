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

/**
 * And filter - Performs logical operator "and" between two images.
 * <br />Logical operators are generally derived from <i>Boolean algebra</i>.
 * <br /><br />Truth-tables for AND: <br /><br />
 * A    B  |    Q <br />
 * --------- <br />
 * 0    0  |    0 <br />
 * 0    1  |    0 <br />
 * 1    0  |    0 <br />
 * 1    1  |    1 <br />
 *
 * @author Diego Catalano
 */
public class And implements Effect {
    private ImageSource mask;

    /**
     * Initialize a new instance of the And class.
     */
    public And() {

    }

    /**
     * Initialize a new instance of the And class with defined an mask image.
     *
     * @param mask Mask image.
     */
    public And(ImageSource mask) {
        this.mask = mask;
    }

    /**
     * Set mask image.
     *
     * @param mask Mask image.
     */
    public And mask(ImageSource mask) {
        this.mask = mask;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int width = input.getWidth();
        int height = input.getHeight();
        int sizeOrigin = width * height;
        int sizeDestination = mask.getWidth() * mask.getHeight();

        if (sizeOrigin != sizeDestination) {
            // TODO throws Runtime exception?
        }
        int grayO, grayS;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //grayS = ColorHelper.getRed(input.getRGB(x, y));
                if (x >= mask.getWidth() || y >= mask.getHeight()) {
                    int background = ColorHelper.getRGB(0, 0, 0);
                    input.setRGB(x, y, background);
                    continue;
                }
                int maskRGB = mask.getRGB(x, y);
                grayO = ColorHelper.getRed(maskRGB);

                if (grayO == 0) {
                    input.setRGB(x, y, maskRGB);
                }
            }
        }


        return input;
    }
}
