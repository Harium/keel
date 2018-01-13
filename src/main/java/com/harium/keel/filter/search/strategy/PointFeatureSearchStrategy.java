package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.Filter;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

public abstract class PointFeatureSearchStrategy extends SearchStrategyImpl<Feature, PointFeature> {

    public PointFeatureSearchStrategy(Filter filter) {
        super(filter);
    }

    protected int getComponentWidth(PointFeature component) {
        int width = component.getW();

        if (width < 0) {
            width = -width;
        }

        width -= filter.getBorder() * 2;
        return width;
    }

    protected int getComponentHeight(PointFeature component) {
        int height = component.getH();

        if (height < 0) {
            height = -height;
        }

        height -= filter.getBorder() * 2;
        return height;
    }
}


