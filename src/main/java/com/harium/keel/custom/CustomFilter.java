package com.harium.keel.custom;

import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.core.strategy.ComponentValidationStrategy;
import com.harium.keel.core.strategy.PixelStrategy;
import com.harium.keel.core.strategy.SearchFilter;

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
