package br.com.etyllica.motion.filter.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.strategy.PixelStrategy;

public class MultipleColorStrategy implements PixelStrategy {

	protected List<ColorStrategy> colors = new ArrayList<ColorStrategy>();
	
	public MultipleColorStrategy() {
		super();
	}
	
	@Override
	public boolean validateColor(int rgb) {
		
		boolean result = false;
		
		for(ColorStrategy strategy: colors) {
			
			if(strategy.validateColor(rgb)) {
				result = true;
				break;
			}
			
		}
		
		return result;
	}
	
	@Override
	public boolean weakValidateColor(int rgb, int reference) {
		return validateColor(rgb);
	}

	public void addColor(Color color, int tolerance) {
		colors.add(new ColorStrategy(color, tolerance));
	}
	
	public void addColor(Color color, int maxTolerance, int minTolerance) {
		colors.add(new ColorStrategy(color, maxTolerance, minTolerance));
	}
	
	public void addColor(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
		colors.add(new ColorStrategy(color, redTolerance, greenTolerance, blueTolerance));
	}
		
}
