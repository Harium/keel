package br.com.etyllica.keel.filter;

import br.com.etyllica.keel.filter.color.skin.SkinColorStrategy;
import br.com.etyllica.keel.filter.search.flood.FloodFillSearch;
import br.com.etyllica.keel.filter.validation.MinCountPoints;

public class TrackingBySkinFilter extends SkinColorFilter {
	
	public TrackingBySkinFilter(int w, int h, int tolerance) {
		super(w, h);

		//FloodFill
		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new SkinColorStrategy(tolerance);
		
		//Set SkinColorStrategy as the Color Strategy
		searchStrategy.setPixelStrategy(colorStrategy);
		
		//Reduce Noise
		searchStrategy.addValidation(new MinCountPoints(10));
		
	}
			
}
