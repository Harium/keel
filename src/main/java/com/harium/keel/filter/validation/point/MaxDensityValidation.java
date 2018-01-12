package com.harium.keel.filter.validation.point;

import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.feature.PointFeature;

public class MaxDensityValidation implements FeatureValidationStrategy<PointFeature> {

    private int density = 100;

    public MaxDensityValidation() {
        super();
    }

    public MaxDensityValidation(int density) {
        super();
        this.density = density;
    }

    @Override
    public boolean validate(PointFeature component) {
        return component.getDensity() <= density;
    }

}
