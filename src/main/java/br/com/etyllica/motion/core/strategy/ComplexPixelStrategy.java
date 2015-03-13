package br.com.etyllica.motion.core.strategy;

import java.util.ArrayList;
import java.util.List;

public class ComplexPixelStrategy implements PixelStrategy {

	private List<PixelStrategy> strategies = new ArrayList<PixelStrategy>();
	
	public ComplexPixelStrategy() {
		super();
	}

	public List<PixelStrategy> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<PixelStrategy> strategies) {
		this.strategies = strategies;
	}
	
	public void addStrategy(PixelStrategy strategy) {
		this.strategies.add(strategy);
	}

	@Override
	public boolean validateColor(int rgb) {
		
		for(PixelStrategy strategy: strategies) {
			
			if (!strategy.validateColor(rgb)){
			
				return false;
				
			}
			
		}
		
		return true;
	}
	
}
