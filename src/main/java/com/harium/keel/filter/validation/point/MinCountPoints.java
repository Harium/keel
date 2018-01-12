package com.harium.keel.filter.validation.point;

import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.feature.PointFeature;

public class MinCountPoints implements FeatureValidationStrategy<PointFeature> {

    private int points = 180;

    public MinCountPoints() {
        super();
    }

    public MinCountPoints(int points) {
        super();
        this.points = points;
    }

    @Override
    public boolean validate(PointFeature component) {

        return component.getPointCount() >= points;

    }

}
