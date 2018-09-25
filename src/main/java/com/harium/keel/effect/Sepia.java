package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class Sepia implements Effect {

    @Override
    public ImageSource apply(ImageSource input) {
        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                int a = input.getA(j, i);
                int r = input.getR(j, i);
                int g = input.getG(j, i);
                int b = input.getB(j, i);

                // YIQ Color Space
                double Y = (0.299 * r) + (0.587 * g) + (0.114 * b);
                //double I = (0.596 * r) - (0.274 * g) - (0.322 * b);
                //double Q = (0.212 * r) - (0.523 * g) + (0.311 * b);

                double I = 51;
                double Q = 0;

                // Transform to RGB
                r = (int) ((1.0 * Y) + (0.956 * I) + (0.621 * Q));
                g = (int) ((1.0 * Y) - (0.272 * I) - (0.647 * Q));
                b = (int) ((1.0 * Y) - (1.105 * I) + (1.702 * Q));

                // Clamp values
                r = ColorHelper.clamp(r);
                g = ColorHelper.clamp(g);
                b = ColorHelper.clamp(b);

                int rgb = ColorHelper.getARGB(r, g, b, a);

                //Set pixels now
                input.setRGB(j, i, rgb);
            }
        }

        return input;
    }


}