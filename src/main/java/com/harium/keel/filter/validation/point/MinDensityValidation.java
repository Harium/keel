package com.harium.keel.filter.validation.point;

import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.feature.PointFeature;

public class MinDensityValidation implements FeatureValidationStrategy<PointFeature> {

    private int density = 80;

    public MinDensityValidation() {
        super();
    }

    public MinDensityValidation(int density) {
        super();

        this.density = density;
    }

    @Override
    public boolean validate(PointFeature component) {
        return component.getDensity() >= density;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

}
