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

import com.harium.keel.catalano.math.Approximation;
import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.helper.EffectHelper;

/**
 * Exp filter.
 * <p>
 * <p><b>Properties:</b>
 * <li>Supported types: Grayscale, RGB.
 * <br><li>Coordinate System: Independent.</p>
 *
 * @author Diego Catalano
 */
public class Exp implements Effect {

    /**
     * Initialize a new instance of the Exp class.
     */
    public Exp() {
    }

    @Override
    public ImageSource apply(ImageSource fastBitmap) {

        // Scale log
        double scale = 255 / Math.log(255);
        if (fastBitmap.isGrayscale()) {
            int size = EffectHelper.getSize(fastBitmap);
            //byte[] pixels = fastBitmap.getGrayData();
            for (int i = 0; i < size; i++) {

                double v = EffectHelper.getRGB(i, fastBitmap);
                v = Approximation.Highprecision_Exp(v / scale);

                // Clip value
                if (v < 0) v = 0;
                if (v > 255) v = 255;

                EffectHelper.setRGB(i, (int) v, fastBitmap);

            }
        } else {

            int size = EffectHelper.getSize(fastBitmap);

            for (int i = 0; i < size; i++) {

                int rgb = EffectHelper.getRGB(i, fastBitmap);
                double r = ColorHelper.getRed(rgb);
                double g = ColorHelper.getGreen(rgb);
                double b = ColorHelper.getBlue(rgb);

                r = Approximation.Highprecision_Exp(r / scale);
                g = Approximation.Highprecision_Exp(g / scale);
                b = Approximation.Highprecision_Exp(b / scale);

                //Clip value
                r = ColorHelper.clamp(r);
                g = ColorHelper.clamp(g);
                b = ColorHelper.clamp(b);

                EffectHelper.setRGB(i, (int) r, (int) g, (int) b, fastBitmap);
            }
        }

        return fastBitmap;
    }
}