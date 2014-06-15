package br.com.etyllica.motion.filter.color;

import java.awt.Color;

import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.search.RightToLeftSearch;

public class RightColorFilter extends ColorFilter {

	public RightColorFilter(int w, int h, Color color) {
		super(w, h);
		
		searchStrategy = new RightToLeftSearch();
		
		colorStrategy = new ColorStrategy(color);
						
		searchStrategy.setPixelStrategy(colorStrategy);

	}
	
}
