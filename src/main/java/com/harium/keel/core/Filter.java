package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.filter.search.strategy.LeftToRightSearchStrategy;
import com.harium.keel.filter.selection.DummySelectionStrategy;
import com.harium.keel.modifier.DummyComponentModifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Base filter class
 *
 * @param <I>
 * @param <T>
 */
public abstract class Filter<I, T> {

    protected int step = 1;
    protected int border = 0;

    protected SearchStrategy<I, T> searchStrategy;
    protected SelectionStrategy selectionStrategy;
    protected ComponentModifierStrategy componentModifierStrategy;

    protected List<T> results = new ArrayList<>();
    protected List<FeatureValidationStrategy<T>> validations = new ArrayList<>();

    public Filter() {
        super();

        this.selectionStrategy = new DummySelectionStrategy();
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();
    }

    public Filter(SelectionStrategy selectionStrategy) {
        super();

        this.selectionStrategy = selectionStrategy;
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();
    }

    public Filter(SelectionStrategy selectionStrategy, FeatureValidationStrategy validationStrategy) {
        super();

        this.selectionStrategy = selectionStrategy;
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();

        this.validations.add(validationStrategy);
    }

    public void setup(ImageSource source, I feature) {
    }

    public void postFilter(ImageSource source, I feature) {
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

    public List<T> filter(ImageSource source, I feature) {
        return searchStrategy.filter(source, feature);
    }

    public T filterFirst(ImageSource source, I feature) {
        return searchStrategy.filterFirst(source, feature);
    }

    public abstract boolean filter(int x, int y, int width, int height, ImageSource source, I component);

    public abstract boolean filterFirst(int x, int y, int width, int height, ImageSource source, I component);

    public List<T> getResults() {
        return results;
    }
}
