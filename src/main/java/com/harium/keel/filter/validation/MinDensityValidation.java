package com.harium.keel.filter.validation;

import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.feature.Component;

public class MinDensityValidation implements ComponentValidationStrategy {

	private int density = 80;
	
	public MinDensityValidation() {
		super();
	}
	
	public MinDensityValidation(int density) {
		super();
		
		this.density = density;
	}
	
	@Override
	public boolean validate(Component component) {
		return component.getDensity() >= density;
	}

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}
	
}
