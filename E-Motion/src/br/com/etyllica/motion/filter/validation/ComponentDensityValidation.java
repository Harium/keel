package br.com.etyllica.motion.filter.validation;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;

public class ComponentDensityValidation implements ComponentValidationStrategy {

	private int density = 80;
	
	public ComponentDensityValidation() {
		super();
	}
	
	public ComponentDensityValidation(int density) {
		super();
		
		this.density = density;
	}
	
	@Override
	public boolean validate(Component component) {

		return component.getPointCount()*100 / component.getArea() >= density;
		
	}
	
}
