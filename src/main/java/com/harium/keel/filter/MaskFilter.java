package com.harium.keel.filter;

import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.color.mask.ImageMaskStrategy;
import com.harium.keel.filter.search.ColoredPointSearch;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;

public class MaskFilter extends TrackingFilter {

	protected int tolerance = 0x40;
	protected ImageMaskStrategy maskStrategy;
	
	public MaskFilter(int w, int h) {
		this(w, h, 0x40);
	}
	
	public MaskFilter(int w, int h, int tolerance) {
		super(new ColoredPointSearch(w, h));
		this.tolerance = tolerance;
		
		this.searchStrategy = new SoftFloodFillSearch(w, h);
		maskStrategy = new ImageMaskStrategy();
		searchStrategy.setPixelStrategy(maskStrategy);
	}
	
	public MaskFilter(ImageSource source) {
		this(source.getWidth(), source.getHeight());
		
		maskStrategy.setMask(source);
	}

	public Component filterFirst(ImageSource bimg, Component component) {
		return searchStrategy.filterFirst(bimg, component);
	}
	
	public List<Component> filter(ImageSource bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
		maskStrategy.setTolerance(tolerance);
	}
	
	public ImageSource getMask() {
		return maskStrategy.getMask();
	}

	public void setMask(ImageSource mask) {
		maskStrategy.setMask(mask);
	}

	public int getTolerance() {
		return tolerance;
	}

}
