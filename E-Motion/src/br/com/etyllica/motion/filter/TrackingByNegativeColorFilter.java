package br.com.etyllica.motion.filter;

import java.awt.Color;

import br.com.etyllica.motion.filter.color.NegativeColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;
import br.com.etyllica.motion.filter.validation.CountComponentPoints;

public class TrackingByNegativeColorFilter extends ColorFilter {
	
	public TrackingByNegativeColorFilter(int w, int h) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
	}
	
	public TrackingByNegativeColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new NegativeColorStrategy(color, 0x40);
		
		searchStrategy.setPixelStrategy(colorStrategy);
		
		searchStrategy.addComponentStrategy(new CountComponentPoints(40));
		
	}
	
	public TrackingByNegativeColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h, color);

		colorStrategy.setTolerance(tolerance);
		
	}
			
}
