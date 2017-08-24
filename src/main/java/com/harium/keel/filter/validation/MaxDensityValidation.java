package com.harium.keel.filter.validation;

import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.feature.Component;

public class MaxDensityValidation implements ComponentValidationStrategy {

	private int density = 80;
	
	public MaxDensityValidation() {
		super();
	}
	
	public MaxDensityValidation(int density) {
		super();
		
		this.density = density;
	}
	
	@Override
	public boolean validate(Component component) {

		return component.getDensity() <= density;
		
	}
	
}
