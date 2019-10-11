package com.harium.keel.filter.track;

import com.harium.keel.filter.SkinColorFilter;
import com.harium.keel.filter.selection.skin.SkinColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.keel.filter.validation.point.MinCountPoints;

public class TrackingBySkinFilter extends SkinColorFilter {
	
	public TrackingBySkinFilter(int w, int h, int tolerance) {
		super(w, h);

		//FloodFill
		this.filter = new FloodFillSearch(w, h);
		
		colorStrategy = new SkinColorStrategy(tolerance);
		
		//Set SkinColorStrategy as the Color Strategy
		filter.setSelectionStrategy(colorStrategy);
		
		//Reduce Noise
		filter.addValidation(new MinCountPoints(10));
		
	}
			
}
