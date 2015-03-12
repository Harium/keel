package br.com.etyllica.motion.custom;

import br.com.etyllica.motion.core.strategy.PixelStrategy;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.motion.core.strategy.SearchFilter;

public abstract class CustomFilter extends SearchFilter{

	protected PixelStrategy colorStrategy;
	
	protected ComponentValidationStrategy componentStrategy;
	
	protected ComponentModifierStrategy modifierStrategy;

	public CustomFilter(){
		super();
	}
	
	public PixelStrategy getPixelStrategy() {
		return colorStrategy;
	}

	public void setPixelStrategy(PixelStrategy colorStrategy) {
		this.colorStrategy = colorStrategy;
	}

	public ComponentValidationStrategy getComponentStrategy() {
		return componentStrategy;
	}

	public void setComponentStrategy(ComponentValidationStrategy componentStrategy) {
		this.componentStrategy = componentStrategy;
	}

	public ComponentModifierStrategy getModifierStrategy() {
		return modifierStrategy;
	}

	public void setModifierStrategy(ComponentModifierStrategy modifierStrategy) {
		this.modifierStrategy = modifierStrategy;
	}
	
}
