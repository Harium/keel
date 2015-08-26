package br.com.etyllica.motion.custom;

import java.awt.Color;

import br.com.etyllica.motion.filter.ColorPointFilter;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.LeftToRightSearch;

public class LeftColorFilter extends ColorPointFilter {

	public LeftColorFilter(int w, int h, Color color) {
		super(w, h);
		
		searchStrategy = new LeftToRightSearch();
		
		colorStrategy = new ColorStrategy(color);
						
		searchStrategy.setPixelStrategy(colorStrategy);

	}
	
}
