package br.com.etyllica.motion.filter;

import java.awt.Color;

import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;

public class HardColorFilter extends ColorFilter {
		
	public HardColorFilter(int w, int h) {
		this(w, h, Color.BLACK);
	}
	
	public HardColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new ColorStrategy(color, 0x40);
		
		searchStrategy.setPixelStrategy(colorStrategy);
	}
				
}
