package com.harium.keel.filter.selection;


import com.harium.etyl.commons.graphics.Color;

public class NegativeColorStrategy extends RGBColorStrategy {

    public NegativeColorStrategy() {
        super();
    }

    public NegativeColorStrategy(Color color) {
        super(color.getRGB());
    }

    public NegativeColorStrategy(Color color, int tolerance) {
        super(color, tolerance);
    }

    public NegativeColorStrategy(int color) {
        super();
        this.color = color;
    }

    @Override
    public boolean valid(int rgb, int x, int y) {
        return !super.valid(rgb, x, y);
    }

}
