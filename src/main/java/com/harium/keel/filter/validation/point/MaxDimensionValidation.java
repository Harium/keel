package com.harium.keel.filter.validation.point;

import com.harium.keel.core.strategy.FeatureValidationStrategy;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

public class MaxDimensionValidation implements FeatureValidationStrategy<PointFeature> {

	private int dimension = 180;
	
	public MaxDimensionValidation() {
		super();
	}
	
	public MaxDimensionValidation(int dimension) {
		super();
		this.dimension = dimension;
	}
	
	@Override
	public boolean validate(PointFeature component) {
		int w = component.getW();
		int h = component.getH();
		return w <= dimension && h <= dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getDimension() {
		return dimension;
	}
	
}
