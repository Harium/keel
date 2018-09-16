package com.harium.keel.core.effect;

import com.harium.etyl.commons.math.EtylMath;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

public class CropEffect implements Effect {

    public static final int UNDEFINED = -1;
    private int width = 1;
    private int height = 1;

    private int cx = UNDEFINED;
    private int cy = UNDEFINED;

    public ImageSource apply(ImageSource input) {
        int[][] out = new int[height][width];

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
                out[j][i] = input.getRGB(i + x, j + y);
            }
        }

        return new MatrixSource(out);
    }

    public CropEffect width(int width) {
        this.width = width;
        return this;
    }

    public CropEffect height(int height) {
        this.height = height;
        return this;
    }

    public CropEffect size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public CropEffect position(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
        return this;
    }

}
