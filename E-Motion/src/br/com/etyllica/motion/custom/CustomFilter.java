package br.com.etyllica.motion.custom;

import br.com.etyllica.motion.core.strategy.ColorStrategy;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.core.strategy.ComponentValidatorStrategy;
import br.com.etyllica.motion.core.strategy.SearchFilter;

public abstract class CustomFilter extends SearchFilter{

	protected ColorStrategy colorStrategy;
	
	protected ComponentValidatorStrategy componentStrategy;
	
	protected ComponentModifierStrategy modifierStrategy;

	public CustomFilter(){
		super();
	}
	
	public ColorStrategy getColorStrategy() {
		return colorStrategy;
	}

	public void setColorStrategy(ColorStrategy colorStrategy) {
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
