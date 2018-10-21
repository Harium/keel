package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class Stamp implements Effect {

    private int x = 0, y = 0;
    private ImageSource stamp;
    private boolean seamless = false;

    @Override
    public ImageSource apply(ImageSource input) {
        if (stamp == null) {
            throw new RuntimeException("Stamp undefined.");
        }
        for (int i = 0; i < stamp.getHeight(); i++) {
            for (int j = 0; j < stamp.getWidth(); j++) {
                int sx = x + j;
                int sy = y + i;

                if (!seamless) {
                    if (sx < 0 || sx >= input.getWidth()) {
                        continue;
                    }
                    if (sy < 0 || sy >= input.getHeight()) {
                        continue;
                    }
                } else {
                    if (sx < 0) {
                        sx += input.getWidth();
                    } else if (sx >= input.getWidth()) {
                        sx %= input.getWidth();
                    }
                    if (sy < 0) {
                        sy += input.getHeight();
                    } else if (sy >= input.getHeight()) {
                        sy %= input.getHeight();
                    }
                }

                int rgb = stamp.getRGB(j, i);
                int inputRGB = input.getRGB(sx, sy);

                input.setRGB(sx, sy, ColorHelper.mix(inputRGB, rgb));
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

    public Stamp seamless(boolean seamless) {
        this.seamless = seamless;
        return this;
    }

    public boolean seamless() {
        return seamless;
    }

}
