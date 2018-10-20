package com.harium.keel.effect.drawing;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class FillRect extends DrawingEffect implements Effect {

    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    @Override
    public ImageSource apply(ImageSource input) {

        int rgb = ColorHelper.getARGB(red, green, blue, alpha);

        if (width == 0) {
            width = input.getWidth() - x;
        }
        if (height == 0) {
            height = input.getHeight() - y;
        }

        for (int i = y; i < height; i++) {
            for (int j = x; j < width; j++) {
                input.setRGB(j, i, rgb);
            }
        }

        return input;
    }

    public FillRect rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

}
