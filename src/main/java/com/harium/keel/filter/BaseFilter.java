package com.harium.keel.filter;

import com.harium.keel.core.Filter;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.feature.Feature;

public abstract class BaseFilter<T> extends Filter<Feature, T> {

    public BaseFilter() {
        super();
    }

    public BaseFilter(SelectionStrategy selectionStrategy) {
        super(selectionStrategy);
    }

    public BaseFilter(SelectionStrategy selectionStrategy, FeatureValidationStrategy validationStrategy) {
        super(selectionStrategy, validationStrategy);
    }

}
