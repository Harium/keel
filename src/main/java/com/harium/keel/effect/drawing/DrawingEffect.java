package com.harium.keel.effect.drawing;

import com.harium.keel.core.helper.ColorHelper;

public abstract class DrawingEffect {
    
    protected int red = 0;
    protected int green = 0;
    protected int blue = 0;
    protected int alpha = 255;

    public DrawingEffect red(int red) {
        this.red = red;
        return this;
    }

    public DrawingEffect green(int green) {
        this.green = green;
        return this;
    }

    public DrawingEffect blue(int blue) {
        this.blue = blue;
        return this;
    }

    public DrawingEffect alpha(int alpha) {
        this.alpha = alpha;
        return this;
    }

    public DrawingEffect rgb(int rgb) {
        this.red = ColorHelper.getRed(rgb);
        this.green = ColorHelper.getGreen(rgb);
        this.blue = ColorHelper.getBlue(rgb);
        this.alpha = ColorHelper.getAlpha(rgb);
        return this;
    }
    
}
