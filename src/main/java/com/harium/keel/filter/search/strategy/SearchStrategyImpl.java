package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.Filter;
import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

public abstract class SearchStrategyImpl<T> implements SearchStrategy<T> {

    protected Filter<T> filter;

    public SearchStrategyImpl(Filter<T> filter) {
        this.filter = filter;
    }

    protected Feature getBounds(Feature component) {
        int border = filter.getBorder();
        int x = component.getX() + border;
        int y = component.getY() + border;
        int width = component.getWidth() - border * 2;
        int height = component.getHeight() - border * 2;
        return new Feature(x, y, width, height);
    }

    protected T first() {
        if (filter.getResults().isEmpty()) {
            return null;
        }
        return filter.getResults().get(0);
    }

}


