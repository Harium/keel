package br.com.etyllica.motion.custom;

import br.com.etyllica.motion.core.strategy.ColorValidatorStrategy;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.core.strategy.ComponentValidatorStrategy;
import br.com.etyllica.motion.core.strategy.SearchFilter;

public abstract class CustomFilter extends SearchFilter{

	protected ColorValidatorStrategy colorStrategy;
	
	protected ComponentValidatorStrategy componentStrategy;
	
	protected ComponentModifierStrategy modifierStrategy;

	public CustomFilter(){
		super();
	}
	
	public ColorValidatorStrategy getColorStrategy() {
		return colorStrategy;
	}

	public void setColorStrategy(ColorValidatorStrategy colorStrategy) {
		this.colorStrategy = colorStrategy;
	}

	public ComponentValidatorStrategy getComponentStrategy() {
		return componentStrategy;
	}

	public void setComponentStrategy(ComponentValidatorStrategy componentStrategy) {
		this.componentStrategy = componentStrategy;
	}

	public ComponentModifierStrategy getModifierStrategy() {
		return modifierStrategy;
	}

	public void setModifierStrategy(ComponentModifierStrategy modifierStrategy) {
		this.modifierStrategy = modifierStrategy;
	}
	
}
