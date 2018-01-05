package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.Filter;
import com.harium.keel.feature.Component;

import java.util.List;

public class DummySearchStrategy extends SearchStrategyImpl implements SearchStrategy {

    public DummySearchStrategy(Filter filter) {
        super(filter);
    }

    @Override
    public Component filterFirst(ImageSource source, Component component) {
        return filter.getResults().get(0);
    }

    @Override
    public List<Component> filter(ImageSource source, Component component) {
        return filter.getResults();
    }
}
