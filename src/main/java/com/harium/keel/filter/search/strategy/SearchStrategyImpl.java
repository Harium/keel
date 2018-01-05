package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.Filter;
import com.harium.keel.feature.Component;

public class SearchStrategyImpl {

    protected Filter filter;

    public SearchStrategyImpl(Filter filter) {
        this.filter = filter;
    }

    protected int getComponentWidth(Component component) {
        int width = component.getW();

        if (width < 0) {
            width = -width;
        }

        width -= filter.getBorder() * 2;
        return width;
    }

    protected int getComponentHeight(Component component) {
        int height = component.getH();

        if (height < 0) {
            height = -height;
        }

        height -= filter.getBorder() * 2;
        return height;
    }

}


