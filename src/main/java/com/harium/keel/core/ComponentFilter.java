package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.PixelStrategy;
import com.harium.keel.feature.Component;


public abstract class ComponentFilter extends Filter {

    protected int w;
    protected int h;

    public ComponentFilter(int w, int h) {
        super();

        this.w = w;
        this.h = h;
    }

    public ComponentFilter(int w, int h, PixelStrategy colorStrategy) {
        super(colorStrategy);

        this.w = w;
        this.h = h;
    }

    @Override
    public void setup(ImageSource source, Component component) {
        int w = component.getW();
        int h = component.getH();
        this.w = w;
        this.h = h;
        super.setup(source, component);
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
