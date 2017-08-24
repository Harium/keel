package com.harium.keel.filter;

import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.color.SimpleToleranceStrategy;
import com.harium.keel.filter.color.skin.SkinColorStrategy;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;

public class SkinColorFilter extends TrackingFilter {

	protected SimpleToleranceStrategy colorStrategy;
		
	public SkinColorFilter(int w, int h) {
		super(new SoftFloodFillSearch(w, h));
	}
		
	public SkinColorFilter(int w, int h, int tolerance) {
		this(w, h);
		
		colorStrategy = new SkinColorStrategy(tolerance);
		searchStrategy.setPixelStrategy(colorStrategy);
	}
	
	public SkinColorFilter(int w, int h, SimpleToleranceStrategy colorStrategy) {
		this(w, h);
		
		this.colorStrategy = colorStrategy;
		searchStrategy.setPixelStrategy(colorStrategy);
	}

	public Component filterFirst(ImageSource bimg, Component component) {
		return searchStrategy.filterFirst(bimg, component);
	}
	
	public List<Component> filter(ImageSource bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	public void setTolerance(int tolerance) {
		colorStrategy.setTolerance(tolerance);
	}

}
