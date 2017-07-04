package br.com.etyllica.keel.filter;

import br.com.etyllica.commons.graphics.Color;
import br.com.etyllica.keel.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.keel.filter.color.DarkerColorStrategy;
import br.com.etyllica.keel.filter.search.flood.FloodFillSearch;

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
