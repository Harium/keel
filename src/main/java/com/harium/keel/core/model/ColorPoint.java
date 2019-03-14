package com.harium.keel.core.model;

import com.harium.etyl.geometry.Point2D;

public class ColorPoint extends Point2D {
    private int color;

    public ColorPoint(double x, double y, int color) {
        super(x, y);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
