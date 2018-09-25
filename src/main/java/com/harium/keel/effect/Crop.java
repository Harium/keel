package com.harium.keel.effect;

import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

public class Crop implements Effect {

    public static final int UNDEFINED = -1;
    private int width = 1;
    private int height = 1;

    private int cx = UNDEFINED;
    private int cy = UNDEFINED;

    public ImageSource apply(ImageSource input) {
        MatrixSource output = new MatrixSource(width, height);

        int w = EtylMath.min(input.getWidth(), width);
        int h = EtylMath.min(input.getHeight(), height);

        int x = cx, y = cy;
        // If undefined, crop on center
        if (cx == UNDEFINED || cy == UNDEFINED) {
            x = (input.getWidth() - w) / 2;
            y = (input.getHeight() - h) / 2;
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                // Just repeat value, alpha included
                output.setRGB(i, j, input.getRGB(i + x, j + y));
            }
        }

        return output;
    }

    public Crop width(int width) {
        this.width = width;
        return this;
    }

    public Crop height(int height) {
        this.height = height;
        return this;
    }

    public Crop size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Crop position(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
        return this;
    }

}
