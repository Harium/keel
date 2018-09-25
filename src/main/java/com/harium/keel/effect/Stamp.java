package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;

public class Stamp implements Effect {

    private int x = 0, y = 0;
    private ImageSource stamp;

    @Override
    public ImageSource apply(ImageSource input) {
        for (int i = 0; i < stamp.getHeight(); i++) {
            for (int j = 0; j < stamp.getWidth(); j++) {
                // Just repeat value, alpha included
                input.setRGB(x + j, y + i, stamp.getRGB(j, i));
            }
        }

        return input;
    }

    public Stamp stamp(ImageSource stamp) {
        this.stamp = stamp;
        return this;
    }

    public Stamp position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Stamp x(int x) {
        this.x = x;
        return this;
    }

    public Stamp y(int y) {
        this.y = y;
        return this;
    }
}
