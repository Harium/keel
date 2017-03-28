package br.com.etyllica.keel.filter.validation;

import br.com.etyllica.keel.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.keel.feature.Component;

public class MinDimensionValidation implements ComponentValidationStrategy {

	private int dimension = 180;
	
	public MinDimensionValidation() {
		super();
	}
	
	public MinDimensionValidation(int dimension) {
		super();
		
		this.dimension = dimension;
	}
	
	@Override
	public boolean validate(Component component) {
		return component.getW() >= dimension && component.getH() >= dimension;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
}
