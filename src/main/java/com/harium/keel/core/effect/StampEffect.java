package com.harium.keel.core.effect;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

public class StampEffect implements Effect {

    private int x = 0, y = 0;
    private ImageSource stamp;

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource out = new MatrixSource(input);

        for (int j = 0; j < stamp.getHeight(); j++) {
            for (int i = 0; i < stamp.getWidth(); i++) {
                out.setRGB(x + i, y + j, input.getRGB(i, j));
            }
        }

        return out;
    }

    public StampEffect stamp(ImageSource stamp) {
        this.stamp = stamp;
        return this;
    }

    public StampEffect position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public StampEffect x(int x) {
        this.x = x;
        return this;
    }

    public StampEffect y(int y) {
        this.y = y;
        return this;
    }
}
