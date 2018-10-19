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
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.core.source.OneBandSource;

/**
 * Maximum filter.
 * <br /> Maximum filter - set maximum pixel values using radius.
 *
 * @author Diego Catalano
 */
public class Maximum extends RadiusEffect implements Effect {

    /**
     * Initialize a new instance of the Maximum class.
     */
    public Maximum() {
        super();
    }

    /**
     * Initialize a new instance of the Maximum class.
     *
     * @param radius Radius.
     */
    public Maximum(int radius) {
        super(radius);
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int width = input.getWidth();
        int height = input.getHeight();

        int Xline, Yline;
        int lines = calcLines(radius);

        if (input.isGrayscale()) {
            OneBandSource copy = OneBandSource.copy(input);
            int maxG;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    maxG = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < width) && (Yline >= 0) && (Yline < height)) {
                                maxG = Math.max(maxG, copy.getRGB(Xline, Yline));
                            }
                        }
                    }
                    input.setRGB(y, x, maxG);
                }
            }
        } else {
            MatrixSource copy = new MatrixSource(input);
            int maxR, maxG, maxB;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    maxR = maxG = maxB = 0;
                    for (int i = 0; i < lines; i++) {
                        Xline = x + (i - radius);
                        for (int j = 0; j < lines; j++) {
                            Yline = y + (j - radius);
                            if ((Xline >= 0) && (Xline < width) && (Yline >= 0) && (Yline < height)) {
                                maxR = Math.max(maxR, copy.getR(Xline, Yline));
                                maxG = Math.max(maxG, copy.getG(Xline, Yline));
                                maxB = Math.max(maxB, copy.getB(Xline, Yline));
                            }
                        }
                    }
                    int rgb = ColorHelper.getRGB(maxR, maxG, maxB);
                    input.setRGB(x, y, rgb);
                }
            }
        }

        return input;
    }

}