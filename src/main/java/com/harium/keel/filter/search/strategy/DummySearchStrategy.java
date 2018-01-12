package com.harium.keel.filter.search.strategy;

import com.harium.keel.core.Filter;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

import java.util.List;

public class DummySearchStrategy extends PointFeatureSearchStrategy {

    public DummySearchStrategy(Filter filter) {
        super(filter);
    }

    @Override
    public PointFeature filterFirst(ImageSource source, Feature component) {
        if(filter.getResults().isEmpty()) {
            return null;
        }
        return filter.getResults().get(0);
    }

    @Override
    public List<PointFeature> filter(ImageSource source, Feature component) {
        return filter.getResults();
    }
}
