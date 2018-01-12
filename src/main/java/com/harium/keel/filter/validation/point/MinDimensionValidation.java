package com.harium.keel.filter.validation.point;

import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.feature.PointFeature;

public class MinDimensionValidation implements FeatureValidationStrategy<PointFeature> {

    private int dimension = 180;

    public MinDimensionValidation() {
        super();
    }

    public MinDimensionValidation(int dimension) {
        super();

        this.dimension = dimension;
    }

    @Override
    public boolean validate(PointFeature component) {
        return component.getW() >= dimension && component.getH() >= dimension;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

}
