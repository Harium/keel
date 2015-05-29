package br.com.etyllica.motion.filter.validation;

import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.motion.feature.Component;

public class CountComponentPoints implements ComponentValidationStrategy {

	private int points = 180;
	
	public CountComponentPoints() {
		super();
	}
	
	public CountComponentPoints(int points) {
		super();
		
		this.points = points;
	}
	
	@Override
	public boolean validate(Component component) {

		return component.getPointCount()>=points;
		
	}
	
}
