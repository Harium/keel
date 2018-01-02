package com.harium.keel.custom;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.core.strategy.PixelStrategy;
import com.harium.keel.core.strategy.SearchFilter;
import com.harium.keel.feature.Component;

import java.util.List;

public abstract class CustomFilter {

    protected SearchFilter filter;

    protected ComponentValidationStrategy validationStrategy;
    protected ComponentModifierStrategy modifierStrategy;

    public CustomFilter() {
        super();
    }

    public CustomFilter(SearchFilter filter) {
        super();
        this.filter = filter;
    }

    public PixelStrategy getPixelStrategy() {
        return filter.getPixelStrategy();
    }

    public void setPixelStrategy(PixelStrategy colorStrategy) {
        filter.setPixelStrategy(colorStrategy);
    }

    public ComponentValidationStrategy getValidationStrategy() {
        return validationStrategy;
    }

    public void setValidationStrategy(ComponentValidationStrategy validationStrategy) {
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

    public void addValidation(ComponentValidationStrategy validation) {
        filter.addValidation(validation);
    }

    public SearchFilter getSearchStrategy() {
        return filter;
    }

    public void setSearchStrategy(SearchFilter searchStrategy) {
        this.filter = searchStrategy;
    }

    public void clearValidations() {
        filter.getValidations().clear();
    }

    public List<Component> filter(ImageSource source, Component component) {
        return filter.filter(source, component);
    }

    public Component filterFirst(ImageSource source, Component component) {
        return filter.filterFirst(source, component);
    }

}
