package com.harium.keel.filter.validation;

import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.feature.Component;

public class MinCountPoints implements ComponentValidationStrategy {

	private int points = 180;
	
	public MinCountPoints() {
		super();
	}
	
	public MinCountPoints(int points) {
		super();
		this.points = points;
	}
	
	@Override
	public boolean validate(Component component) {

		return component.getPointCount()>=points;
		
	}
	
}
