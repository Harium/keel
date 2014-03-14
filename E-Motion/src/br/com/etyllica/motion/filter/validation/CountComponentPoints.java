package br.com.etyllica.motion.filter.validation;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.ComponentValidatorStrategy;

public class CountComponentPoints implements ComponentValidatorStrategy {

	private int points = 180;
	
	public CountComponentPoints() {
		super();
	}
	
	public CountComponentPoints(int points) {
		super();
		
		this.points = points;
	}
	
	@Override
	public boolean validateComponent(Component component) {

		return component.getNumeroPontos()>=points;
		
	}
	
}
