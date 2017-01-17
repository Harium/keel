package br.com.etyllica.motion.core.strategy;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.SearchStrategy;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.dumb.DumbColorFilter;
import br.com.etyllica.motion.filter.dumb.DumbComponentModifier;


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
		
		this.pixelStrategy = new DumbColorFilter();
				
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SearchFilter(PixelStrategy colorStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public SearchFilter(PixelStrategy colorStrategy, ComponentValidationStrategy componentStrategy) {
		super();
		
		this.pixelStrategy = colorStrategy;
		
		this.validations.add(componentStrategy);
		
		this.componentModifierStrategy = new DumbComponentModifier();
	}
	
	public void setup(){
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
