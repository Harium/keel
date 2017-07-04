package br.com.etyllica.keel.custom;


import br.com.etyllica.commons.graphics.Color;
import br.com.etyllica.keel.filter.ColorPointFilter;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.RightToLeftSearch;

public class RightColorFilter extends ColorPointFilter {

	public RightColorFilter(int w, int h, Color color) {
		super(w, h);
		
		searchStrategy = new RightToLeftSearch();
		
		colorStrategy = new ColorStrategy(color);
						
		searchStrategy.setPixelStrategy(colorStrategy);

	}
	
}
