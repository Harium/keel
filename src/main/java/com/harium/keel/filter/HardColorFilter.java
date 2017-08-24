package com.harium.keel.filter;

import com.harium.keel.filter.color.ColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.etyl.commons.graphics.Color;

public class HardColorFilter extends ColorFilter {
	
	public HardColorFilter(int w, int h) {
		this(w, h, Color.BLACK);
	}
	
	public HardColorFilter(int w, int h, Color color) {
		this(w, h, color, 0x40);
	}
	
	public HardColorFilter(int w, int h, Color color, int tolerance) {
		super(w, h);
		
		this.tolerance = tolerance;

		this.searchStrategy = new FloodFillSearch(w, h);
		colorStrategy = new ColorStrategy(color, tolerance);
		searchStrategy.setPixelStrategy(colorStrategy);
	}
				
}
