package com.harium.keel.filter;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.custom.CustomFilter;
import com.harium.keel.filter.color.mask.ImageMaskStrategy;
import com.harium.keel.filter.search.ColoredPointSearch;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;

public class MaskFilter extends CustomFilter {

	protected int tolerance = 0x40;
	protected ImageMaskStrategy maskStrategy;
	
	public MaskFilter(int w, int h) {
		this(w, h, 0x40);
	}
	
	public MaskFilter(int w, int h, int tolerance) {
		super(new ColoredPointSearch(w, h, Color.BLACK));
		this.tolerance = tolerance;

		filter = new SoftFloodFillSearch(w, h);
		maskStrategy = new ImageMaskStrategy();
		setPixelStrategy(maskStrategy);
	}
	
	public MaskFilter(ImageSource source) {
		this(source.getWidth(), source.getHeight());
		maskStrategy.setMask(source);
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
