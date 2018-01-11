package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.Filter;
import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.feature.Component;

import java.util.List;

public class LeftToRightSearchStrategy extends SearchStrategyImpl implements SearchStrategy {

    public LeftToRightSearchStrategy(Filter filter) {
        super(filter);
    }

    public Component filterFirst(final ImageSource source, final Component component) {
        filter.setup(source, component);
        filter.getLastComponent().reset();

        int x = component.getX() + filter.getBorder();
        int y = component.getY() + filter.getBorder();

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        Component bounds = new Component();
        bounds.setBounds(x, y, width, height);

        for (int j = y; j < y + height; j += filter.getStep()) {
            for (int i = x; i < x + width; i += filter.getStep()) {
                if (!bounds.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filterFirst(i, j, width, height, source, bounds)) {
                    filter.postFilter(source, component);
                    return filter.getLastComponent();
                }
            }
        }

        filter.postFilter(source, component);
        return filter.getLastComponent();
    }

    public List<Component> filter(final ImageSource source, final Component component) {
        filter.setup(source, component);

        int x = component.getX() + filter.getBorder();
        int y = component.getY() + filter.getBorder();

        int width = getComponentWidth(component);
        int height = getComponentHeight(component);

        Component bounds = new Component(x, y, width, height);

        for (int j = y; j < y + height; j += filter.getStep()) {
            for (int i = x; i < x + width; i += filter.getStep()) {
                if (!bounds.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filter(i, j, width, height, source, bounds)) {
                    filter.postFilter(source, component);
                    return filter.getResults();
                }
            }
        }

        filter.postFilter(source, component);
        return filter.getResults();
    }

}
