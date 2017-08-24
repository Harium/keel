package com.harium.keel.core.strategy;

import java.util.ArrayList;
import java.util.List;

import com.harium.keel.core.SearchStrategy;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.dummy.DummyColorFilter;
import com.harium.keel.filter.dummy.DummyComponentModifier;


public abstract class SearchFilter implements SearchStrategy {

	protected int step = 1;
	
	protected int border = 1;
		
	protected PixelStrategy pixelStrategy;
			
	protected ComponentModifierStrategy componentModifierStrategy;
	
	protected Component lastComponent = new Component(0, 0, 1, 1);
	
	protected List<Component> result = new ArrayList<Component>();
	
	protected List<ComponentValidationStrategy> validations = new ArrayList<ComponentValidationStrategy>();
	
	public SearchFilter() {
		super();
		
		this.pixelStrategy = new DummyColorFilter();
				
		this.componentModifierStrategy = new DummyComponentModifier();
	}
	
	public SearchFilter(PixelStrategy colorStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.componentModifierStrategy = new DummyComponentModifier();
	}
	
	public SearchFilter(PixelStrategy colorStrategy, ComponentValidationStrategy componentStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.validations.add(componentStrategy);
		
		this.componentModifierStrategy = new DummyComponentModifier();
	}
	
	@Override
	public void setup(int w, int h) {
		result = new ArrayList<Component>();
	}

	public PixelStrategy getPixelStrategy() {
		return pixelStrategy;
	}

	public void setPixelStrategy(PixelStrategy colorStrategy) {
		this.pixelStrategy = colorStrategy;
	}

	public List<ComponentValidationStrategy> getValidations() {
		return validations;
	}

	public void addValidation(ComponentValidationStrategy validation) {
		this.validations.add(validation);
	}
	
	public void setComponentStrategy(List<ComponentValidationStrategy> validations) {
		this.validations = validations;
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
	
	protected boolean validate(Component component) {
		
		for(ComponentValidationStrategy validation : validations) {
			if (!validation.validate(component)) {
				return false;
			}
		}
		
		return true;
	}
	
	protected int getComponentWidth(Component component) {
		int width = component.getW();
		
		if(width < 0) {
			width = -width;
		}
		
		width -= border*2;
		return width;
	}

	protected int getComponentHeight(Component component) {
		int height = component.getH();
		
		if(height < 0) {
			height = -height;
		}
		
		height -= border*2;
		return height;
	}
	
}
