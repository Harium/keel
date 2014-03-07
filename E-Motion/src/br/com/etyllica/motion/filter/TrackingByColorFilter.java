package br.com.etyllica.motion.filter;

import java.awt.Color;

import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.dumb.DumbComponentFilter;
import br.com.etyllica.motion.filter.search.FloodFillSearch;

public class TrackingByColorFilter extends ColorFilter {
	
	public TrackingByColorFilter(int w, int h) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
	}
	
	public TrackingByColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new ColorStrategy(color);
		
		searchStrategy.setPixelStrategy(colorStrategy);
		
		searchStrategy.setComponentStrategy(new DumbComponentFilter());
		
	}
			
}
