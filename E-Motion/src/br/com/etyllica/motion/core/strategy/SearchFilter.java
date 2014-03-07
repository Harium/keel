package br.com.etyllica.motion.core.strategy;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.SearchStrategy;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.dumb.DumbColorFilter;
import br.com.etyllica.motion.filter.dumb.DumbComponentFilter;
import br.com.etyllica.motion.filter.dumb.DumbComponentModifier;


public abstract class SearchFilter implements SearchStrategy {

	protected int step = 1;
	
	protected int border = 0;
		
	protected PixelStrategy pixelStrategy;
	
	protected ComponentValidatorStrategy componentStrategy;
	
	protected ComponentModifierStrategy componentModifierStrategy;
	
	protected Component lastComponent = new Component(0, 0, 1, 1);
	
	protected List<Component> result = new ArrayList<Component>();
	
	public SearchFilter() {
		super();
		
		this.pixelStrategy = new DumbColorFilter();
		
		this.componentStrategy = new DumbComponentFilter();
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SearchFilter(PixelStrategy colorStrategy, ComponentValidatorStrategy componentStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.componentStrategy = componentStrategy;
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public void setup(){
		result.clear();	
	}

	public PixelStrategy getPixelStrategy() {
		return pixelStrategy;
	}

	public void setPixelStrategy(PixelStrategy colorStrategy) {
		this.pixelStrategy = colorStrategy;
	}

	public ComponentValidatorStrategy getComponentStrategy() {
		return componentStrategy;
	}

	public void setComponentStrategy(ComponentValidatorStrategy componentStrategy) {
		this.componentStrategy = componentStrategy;
	}
	
	public ComponentModifierStrategy getComponentModifierStrategy() {
		return componentModifierStrategy;
	}

	public void setComponentModifierStrategy(ComponentModifierStrategy componentModifierStrategy) {
		this.componentModifierStrategy = componentModifierStrategy;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}
			
}
