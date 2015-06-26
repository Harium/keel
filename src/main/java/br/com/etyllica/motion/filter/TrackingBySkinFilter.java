package br.com.etyllica.motion.filter;

import br.com.etyllica.motion.filter.color.SkinColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;
import br.com.etyllica.motion.filter.validation.MinCountPoints;

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
