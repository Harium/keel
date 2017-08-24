package com.harium.keel.filter.color;


import com.harium.etyl.commons.graphics.Color;

public class NegativeColorStrategy extends ColorStrategy {

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
    public boolean validateColor(int rgb, int j, int i) {
        return !super.validateColor(rgb, j, i);
    }

}
