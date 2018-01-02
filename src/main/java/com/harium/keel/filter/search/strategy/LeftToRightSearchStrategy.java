package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.SearchFilter;
import com.harium.keel.feature.Component;

import java.util.List;

public class LeftToRightSearchStrategy extends SearchStrategyImpl implements SearchStrategy {

    public LeftToRightSearchStrategy(SearchFilter filter) {
        super(filter);
    }

    public Component filterFirst(final ImageSource source, final Component component) {
        filter.setup(component.getW(), component.getH());

        int x = component.getX() + filter.getBorder();
        int y = component.getY() + filter.getBorder();

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        filter.getLastComponent().reset();

        for (int j = y; j < y + height; j += filter.getStep()) {
            for (int i = x; i < x + width; i += filter.getStep()) {
                if (!component.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filterFirst(x, y, width, height, source)) {
                    return filter.getLastComponent();
                }
            }
        }

        return filter.getLastComponent();
    }

    public List<Component> filter(final ImageSource source, final Component component) {
        filter.setup(component.getW(), component.getH());

        int x = component.getX() + filter.getBorder();
        int y = component.getY() + filter.getBorder();

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        for (int j = y; j < y + height; j += filter.getStep()) {
            for (int i = x; i < x + width; i += filter.getStep()) {
                if (!component.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filter(x, y, width, height, source)) {
                    return filter.getResults();
                }
            }
        }

        return filter.getResults();
    }

}
