package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.filter.dummy.DummyColorFilter;
import com.harium.keel.filter.dummy.DummyComponentModifier;
import com.harium.keel.filter.search.strategy.LeftToRightSearchStrategy;

import java.util.ArrayList;
import java.util.List;


public abstract class Filter<T> {

    protected int step = 1;
    protected int border = 1;

    protected SearchStrategy<T> searchStrategy;
    protected SelectionStrategy selectionStrategy;
    protected ComponentModifierStrategy componentModifierStrategy;

    protected List<T> results = new ArrayList<>();
    protected List<FeatureValidationStrategy<T>> validations = new ArrayList<>();

    public Filter() {
        super();

        this.selectionStrategy = new DummyColorFilter();
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();
    }

    public Filter(SelectionStrategy colorStrategy) {
        super();

        this.selectionStrategy = colorStrategy;
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();
    }

    public Filter(SelectionStrategy colorStrategy, FeatureValidationStrategy componentStrategy) {
        super();

        this.selectionStrategy = colorStrategy;
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();

        this.validations.add(componentStrategy);
    }

    public void setup(ImageSource source, Feature feature) {
    }

    public void postFilter(ImageSource source, Feature feature) {
    }

    public SelectionStrategy getSelectionStrategy() {
        return selectionStrategy;
    }

    public void setSelectionStrategy(SelectionStrategy colorStrategy) {
        this.selectionStrategy = colorStrategy;
    }

    public SearchStrategy getSearchStrategy() {
        return searchStrategy;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<FeatureValidationStrategy<T>> getValidations() {
        return validations;
    }

    public void addValidation(FeatureValidationStrategy validation) {
        this.validations.add(validation);
    }

    public void setComponentStrategy(List<FeatureValidationStrategy<T>> validations) {
        this.validations = validations;
    }

    public ComponentModifierStrategy getComponentModifierStrategy() {
        return componentModifierStrategy;
    }

    public void setComponentModifierStrategy(ComponentModifierStrategy componentModifierStrategy) {
        this.componentModifierStrategy = componentModifierStrategy;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    protected boolean validate(T feature) {
        for (FeatureValidationStrategy validation : validations) {
            if (!validation.validate(feature)) {
                return false;
            }
        }

        return true;
    }

    public List<T> filter(ImageSource source, Feature component) {
        return searchStrategy.filter(source, component);
    }

    public T filterFirst(ImageSource source, Feature component) {
        return searchStrategy.filterFirst(source, component);
    }

    public abstract boolean filter(int x, int y, int width, int height, ImageSource source, Feature component);

    public abstract boolean filterFirst(int x, int y, int width, int height, ImageSource source, Feature component);

    public List<T> getResults() {
        return results;
    }
}
