package br.com.etyllica.keel.core.strategy;

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
	public boolean validateColor(int rgb, int j, int i) {
		
		for(PixelStrategy strategy: strategies) {
			if (!strategy.validateColor(rgb, j, i)){
				return false;
			}
		}	
		return true;
	}
	
	@Override
	public boolean strongValidateColor(int rgb, int j, int i, int reference) {
		return validateColor(rgb, j, i);
	}
	
}
