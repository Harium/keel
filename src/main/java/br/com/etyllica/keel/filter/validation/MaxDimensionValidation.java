package br.com.etyllica.keel.filter.validation;

import br.com.etyllica.keel.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.keel.feature.Component;

public class MaxDimensionValidation implements ComponentValidationStrategy {

	private int dimension = 180;
	
	public MaxDimensionValidation() {
		super();
	}
	
	public MaxDimensionValidation(int dimension) {
		super();
		this.dimension = dimension;
	}
	
	@Override
	public boolean validate(Component component) {
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
