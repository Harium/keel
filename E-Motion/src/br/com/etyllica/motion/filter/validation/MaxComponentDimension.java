package br.com.etyllica.motion.filter.validation;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;

public class MaxComponentDimension implements ComponentValidationStrategy {

	private int dimension = 180;
	
	public MaxComponentDimension() {
		super();
	}
	
	public MaxComponentDimension(int dimension) {
		super();
		
		this.dimension = dimension;
	}
	
	@Override
	public boolean validate(Component component) {

		return component.getW()<=dimension||component.getH()<=dimension;
		
	}
	
}
