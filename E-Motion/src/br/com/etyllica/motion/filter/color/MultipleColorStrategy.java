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
		
		boolean result = true;
		
		for(ColorStrategy strategy: colors) {
			
			if(!strategy.validateColor(rgb)) {
				result = false;
				break;
			}
			
		}
		
		return result;
	}

	public void addColor(Color color, int tolerance) {
		colors.add(new ColorStrategy(color, tolerance));
	}
		
}
