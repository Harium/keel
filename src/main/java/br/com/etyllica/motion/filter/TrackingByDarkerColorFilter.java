package br.com.etyllica.motion.filter;

import java.awt.Color;

import br.com.etyllica.motion.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.motion.filter.color.DarkerColorStrategy;
import br.com.etyllica.motion.filter.search.flood.FloodFillSearch;

public class TrackingByDarkerColorFilter extends ColorPointFilter {
	
	public TrackingByDarkerColorFilter(int w, int h) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
	}
	
	public TrackingByDarkerColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new DarkerColorStrategy(color, 0x40);
		
		searchStrategy.setPixelStrategy(colorStrategy);
				
	}
	
	public TrackingByDarkerColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h, color);

		colorStrategy.setTolerance(tolerance);
		
	}
	
	public void addValidation(ComponentValidationStrategy validation) {
		searchStrategy.addValidation(validation);
	}
			
}
