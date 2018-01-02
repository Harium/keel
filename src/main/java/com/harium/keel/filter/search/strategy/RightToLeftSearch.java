package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.SearchFilter;
import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.feature.Component;

import java.util.List;

public class RightToLeftSearch extends SearchStrategyImpl implements SearchStrategy {

    public RightToLeftSearch(SearchFilter filter) {
        super(filter);
    }

    public Component filterFirst(ImageSource source, Component component) {
        filter.setup(source, component);

        int x = component.getX() + filter.getBorder();
        int y = component.getY() + filter.getBorder();

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        for (int j = y; j < y + height; j += filter.getStep()) {
            for (int i = x + width; i > x; i -= filter.getStep()) {

                if (!component.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filterFirst(i, j, width, height, source, component)) {
                    return filter.getLastComponent();
                }
            }
        }

        return filter.getLastComponent();
    }

    @Override
    public List<Component> filter(ImageSource source, Component component) {
        filter.setup(source, component);

        int x = component.getX() + filter.getBorder();
        int y = component.getY() + filter.getBorder();

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        for (int j = y; j < y + height; j += filter.getStep()) {
            for (int i = x + width; i > x; i -= filter.getStep()) {

                if (!component.isInside(i, j)) {
                    continue;
                }
                // Filter returns true to stop early
                if (filter.filter(i, j, width, height, source, component)) {
                    return filter.getResults();
                }
            }
        }

        return filter.getResults();
    }

}
