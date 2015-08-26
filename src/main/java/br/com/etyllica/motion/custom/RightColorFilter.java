package br.com.etyllica.motion.custom;

import java.awt.Color;

import br.com.etyllica.motion.filter.ColorPointFilter;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.RightToLeftSearch;

public class RightColorFilter extends ColorPointFilter {

	public RightColorFilter(int w, int h, Color color) {
		super(w, h);
		
		searchStrategy = new RightToLeftSearch();
		
		colorStrategy = new ColorStrategy(color);
						
		searchStrategy.setPixelStrategy(colorStrategy);

	}
	
}
