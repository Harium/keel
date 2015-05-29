package br.com.etyllica.motion.filter.validation;

import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.motion.feature.Component;

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
