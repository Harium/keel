package br.com.etyllica.keel.custom;

import java.awt.Color;

import br.com.etyllica.keel.filter.ColorPointFilter;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.LeftToRightSearch;

public class LeftColorFilter extends ColorPointFilter {

	public LeftColorFilter(int w, int h, Color color) {
		super(w, h);
		
		searchStrategy = new LeftToRightSearch();
		
		colorStrategy = new ColorStrategy(color);
						
		searchStrategy.setPixelStrategy(colorStrategy);

	}
	
}
