package br.com.etyllica.keel.filter.validation;

import br.com.etyllica.keel.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.keel.feature.Component;

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
