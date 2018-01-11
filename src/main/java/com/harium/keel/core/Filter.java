package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.core.strategy.PixelStrategy;
import com.harium.keel.core.strategy.SearchStrategy;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.dummy.DummyColorFilter;
import com.harium.keel.filter.dummy.DummyComponentModifier;
import com.harium.keel.filter.search.strategy.LeftToRightSearchStrategy;

import java.util.ArrayList;
import java.util.List;


public abstract class Filter {

    protected int step = 1;
    protected int border = 1;

    protected SearchStrategy searchStrategy;
    protected PixelStrategy pixelStrategy;
    protected ComponentModifierStrategy componentModifierStrategy;

    protected Component lastComponent = new Component(0, 0, 1, 1);

    protected List<Component> results = new ArrayList<Component>();

    protected List<ComponentValidationStrategy> validations = new ArrayList<ComponentValidationStrategy>();

    public Filter() {
        super();

        this.pixelStrategy = new DummyColorFilter();
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();
    }

    public Filter(PixelStrategy colorStrategy) {
        super();

        this.pixelStrategy = colorStrategy;
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();
    }

    public Filter(PixelStrategy colorStrategy, ComponentValidationStrategy componentStrategy) {
        super();

        this.pixelStrategy = colorStrategy;
        this.searchStrategy = new LeftToRightSearchStrategy(this);
        this.componentModifierStrategy = new DummyComponentModifier();

        this.validations.add(componentStrategy);
    }

    public void setup(ImageSource source, Component component) {
        results = new ArrayList<Component>();
    }

    public void postFilter(ImageSource source, Component component) {}

    public PixelStrategy getPixelStrategy() {
        return pixelStrategy;
    }

    public void setPixelStrategy(PixelStrategy colorStrategy) {
        this.pixelStrategy = colorStrategy;
    }

    public SearchStrategy getSearchStrategy() {
        return searchStrategy;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<ComponentValidationStrategy> getValidations() {
        return validations;
    }

    public void addValidation(ComponentValidationStrategy validation) {
        this.validations.add(validation);
    }

    public void setComponentStrategy(List<ComponentValidationStrategy> validations) {
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

    protected boolean validate(Component component) {

        for (ComponentValidationStrategy validation : validations) {
            if (!validation.validate(component)) {
                return false;
            }
        }

        return true;
    }

    public List<Component> filter(ImageSource source, Component component) {
        return searchStrategy.filter(source, component);
    }

    public Component filterFirst(ImageSource source, Component component) {
        return searchStrategy.filterFirst(source, component);
    }

    public abstract boolean filter(int x, int y, int width, int height, ImageSource source, Component component);

    public abstract boolean filterFirst(int x, int y, int width, int height, ImageSource source, Component component);

    public Component getLastComponent() {
        return lastComponent;
    }

    public List<Component> getResults() {
        return results;
    }
}
