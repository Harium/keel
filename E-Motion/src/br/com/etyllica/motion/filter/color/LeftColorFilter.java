package br.com.etyllica.motion.filter.color;

import java.awt.Color;

import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.search.LeftToRightSearch;

public class LeftColorFilter extends ColorFilter {

	public LeftColorFilter(int w, int h, Color color) {
		super(w, h);
		
		searchStrategy = new LeftToRightSearch();
		
		colorStrategy = new ColorStrategy(color);
						
		searchStrategy.setPixelStrategy(colorStrategy);

	}
	
}
