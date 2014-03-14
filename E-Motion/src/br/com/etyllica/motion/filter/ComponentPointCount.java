package br.com.etyllica.motion.filter;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.ComponentValidatorStrategy;

public class ComponentPointCount implements ComponentValidatorStrategy {

	private int points = 180;
	
	public ComponentPointCount(){
		super();
	}
	
	public ComponentPointCount(int points){
		super();
		
		this.points = points;
	}
	
	@Override
	public boolean validateComponent(Component component) {

		return component.getNumeroPontos()>points;
		
	}
	
	

}
