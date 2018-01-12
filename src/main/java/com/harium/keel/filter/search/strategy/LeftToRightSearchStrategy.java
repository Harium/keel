package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.Filter;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

import java.util.List;

public class LeftToRightSearchStrategy<T> extends SearchStrategyImpl<T> {

    public LeftToRightSearchStrategy(Filter<T> filter) {
        super(filter);
    }

    public T filterFirst(final ImageSource source, final Feature component) {
        filter.setup(source, component);
        Feature bounds = getBounds(component);

        for (int j = bounds.getY(); j < bounds.getY() + bounds.getHeight(); j += filter.getStep()) {
            for (int i = bounds.getX(); i < bounds.getX() + bounds.getWidth(); i += filter.getStep()) {
                if (!bounds.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filterFirst(i, j, bounds.getWidth(), bounds.getHeight(), source, bounds)) {
                    filter.postFilter(source, component);
                    return first();
                }
            }
        }

        filter.postFilter(source, component);
        return first();
    }



    public List<T> filter(final ImageSource source, final Feature component) {
        filter.setup(source, component);
        Feature bounds = getBounds(component);

        for (int j = bounds.getY(); j < bounds.getY() + bounds.getHeight(); j += filter.getStep()) {
            for (int i = bounds.getX(); i < bounds.getX() + bounds.getWidth(); i += filter.getStep()) {
                if (!bounds.isInside(i, j)) {
                    continue;
                }

                // Filter returns true to stop early
                if (filter.filter(i, j, bounds.getWidth(), bounds.getHeight(), source, bounds)) {
                    filter.postFilter(source, component);
                    return filter.getResults();
                }
            }
        }

        filter.postFilter(source, component);
        return filter.getResults();
    }

}
