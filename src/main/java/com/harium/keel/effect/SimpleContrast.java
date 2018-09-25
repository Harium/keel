package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class SimpleContrast implements Effect {

    private int contrast = 10;

    public SimpleContrast() {
        super();
    }

    public SimpleContrast(int contrast) {
        super();
        this.contrast = contrast;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        int w = input.getWidth();
        int h = input.getHeight();

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {

                int rgb = input.getRGB(i, j);

                int alpha = ColorHelper.getAlpha(rgb);
                int red = ColorHelper.getRed(rgb);
                int green = ColorHelper.getGreen(rgb);
                int blue = ColorHelper.getBlue(rgb);

                int average = (red + green + blue) / 3;

                //Bright pixel
                if (average > 128) {
                    red = brighterPixel(red);
                    green = brighterPixel(green);
                    blue = brighterPixel(blue);
                } else {
                    red = darkerPixel(red);
                    green = darkerPixel(green);
                    blue = darkerPixel(blue);
                }

                rgb = ColorHelper.getARGB(red, green, blue, alpha);
                input.setRGB(i, j, rgb);
            }
        }

        return input;
    }

    private int brighterPixel(int pixel) {
        if (pixel < 255 - contrast) {
            return pixel + contrast;
        } else {
            return 255;
        }
    }

    private int darkerPixel(int pixel) {
        if (pixel > contrast) {
            return pixel - contrast;
        } else {
            return 0;
        }
    }

    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    public SimpleContrast contratst() {
        this.contrast = contrast;
        return this;
    }
}
