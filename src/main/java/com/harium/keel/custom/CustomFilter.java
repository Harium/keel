package com.harium.keel.custom;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.core.strategy.SelectionStrategy;
import com.harium.keel.core.Filter;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

import java.util.List;

public abstract class CustomFilter<T> {

    protected Filter<T> filter;

    protected FeatureValidationStrategy validationStrategy;
    protected ComponentModifierStrategy modifierStrategy;

    public CustomFilter() {
        super();
    }

    public CustomFilter(Filter<T> filter) {
        super();
        this.filter = filter;
    }

    public SelectionStrategy getPixelStrategy() {
        return filter.getSelectionStrategy();
    }

    public void setPixelStrategy(SelectionStrategy colorStrategy) {
        filter.setSelectionStrategy(colorStrategy);
    }

    public FeatureValidationStrategy getValidationStrategy() {
        return validationStrategy;
    }

    public void setValidationStrategy(FeatureValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    public int getBorder() {
        return filter.getBorder();
    }

    public void setBorder(int border) {
        filter.setBorder(border);
    }

    public int getStep() {
        return filter.getStep();
    }

    public void setStep(int step) {
        filter.setStep(step);
    }

    public ComponentModifierStrategy getModifierStrategy() {
        return modifierStrategy;
    }

    public void setModifierStrategy(ComponentModifierStrategy modifierStrategy) {
        this.modifierStrategy = modifierStrategy;
    }

    public void addValidation(FeatureValidationStrategy validation) {
        filter.addValidation(validation);
    }

    public Filter getSearchStrategy() {
        return filter;
    }

    public void setSearchStrategy(Filter searchStrategy) {
        this.filter = searchStrategy;
    }

    public void clearValidations() {
        filter.getValidations().clear();
    }

    public T filterFirst(ImageSource source, Feature component) {
        return filter.filterFirst(source, component);
    }

    public List<T> filter(ImageSource source, Feature component) {
        return filter.filter(source, component);
    }

}
