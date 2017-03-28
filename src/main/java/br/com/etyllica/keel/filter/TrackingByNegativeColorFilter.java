package br.com.etyllica.keel.filter;

import java.awt.Color;

import br.com.etyllica.keel.core.strategy.ComponentValidationStrategy;
import br.com.etyllica.keel.filter.color.NegativeColorStrategy;
import br.com.etyllica.keel.filter.search.flood.FloodFillSearch;

public class TrackingByNegativeColorFilter extends ColorPointFilter {
	
	public TrackingByNegativeColorFilter(int w, int h) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
	}
	
	public TrackingByNegativeColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new NegativeColorStrategy(color, 0x40);
		
		searchStrategy.setPixelStrategy(colorStrategy);
				
	}
	
	public TrackingByNegativeColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h, color);

		colorStrategy.setTolerance(tolerance);
		
	}
	
	public void addValidation(ComponentValidationStrategy validation) {
		searchStrategy.addValidation(validation);
	}
			
}
