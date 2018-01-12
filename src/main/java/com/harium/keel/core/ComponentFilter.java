package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.feature.PointFeature;

import java.util.ArrayList;


public abstract class ComponentFilter extends Filter<PointFeature> {

    protected int w;
    protected int h;

    protected PointFeature lastComponent = new PointFeature(0, 0, 1, 1);

    public ComponentFilter(int w, int h) {
        super();

        this.w = w;
        this.h = h;
    }

    public ComponentFilter(int w, int h, SelectionStrategy colorStrategy) {
        super(colorStrategy);

        this.w = w;
        this.h = h;
    }

    public void setup(ImageSource source, PointFeature feature) {
        int w = feature.getW();
        int h = feature.getH();
        this.w = w;
        this.h = h;

        results = new ArrayList<>();
        lastComponent.reset();
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

    public PointFeature getLastComponent() {
        return lastComponent;
    }
}
