package br.com.etyllica.motion.core.strategy;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.Filter;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.dumb.DumbColorFilter;
import br.com.etyllica.motion.filter.dumb.DumbComponentFilter;
import br.com.etyllica.motion.filter.dumb.DumbComponentModifier;


public abstract class SearchFilter implements Filter {

	protected int step = 1;
	
	protected int border = 0;
		
	protected ColorValidatorStrategy colorStrategy;
	
	protected ComponentValidatorStrategy componentStrategy;
	
	protected ComponentModifierStrategy componentModifierStrategy;
	
	protected List<Component> result = new ArrayList<Component>();
	
	public SearchFilter() {
		super();
		
		this.colorStrategy = new DumbColorFilter();
		
		this.componentStrategy = new DumbComponentFilter();
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SearchFilter(ColorValidatorStrategy colorStrategy, ComponentValidatorStrategy componentStrategy) {
		super();
		
		this.colorStrategy = colorStrategy;
		
		this.componentStrategy = componentStrategy;
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public static int difMod(int a, int b){
		if(a>b){
			return a-b;
		}else{
			return b-a;
		}
	}
	
	public void setup(){
		result.clear();	
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
