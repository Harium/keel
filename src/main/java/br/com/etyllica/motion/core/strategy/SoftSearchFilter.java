package br.com.etyllica.motion.core.strategy;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.motion.filter.dumb.DumbColorFilter;
import br.com.etyllica.motion.filter.dumb.DumbComponentModifier;

public abstract class SoftSearchFilter extends SearchFilter {
		
	protected SoftPixelStrategy pixelStrategy;

	public SoftSearchFilter() {
		super();
		
		this.pixelStrategy = new DumbColorFilter();
				
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SoftSearchFilter(SoftPixelStrategy colorStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SoftSearchFilter(SoftPixelStrategy colorStrategy, ComponentValidationStrategy componentStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.validations.add(componentStrategy);
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SoftPixelStrategy getPixelStrategy() {
		return pixelStrategy;
	}

	public void setPixelStrategy(SoftPixelStrategy colorStrategy) {
		this.pixelStrategy = colorStrategy;
	}
	
}
