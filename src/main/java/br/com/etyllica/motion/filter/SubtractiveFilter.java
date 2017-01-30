package br.com.etyllica.motion.filter;

import java.util.List;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.mask.ImageMaskStrategy;
import br.com.etyllica.motion.filter.search.ColoredPointSearch;
import br.com.etyllica.motion.filter.search.flood.SoftFloodFillSearch;

public class SubtractiveFilter extends TrackingFilter {

	protected int tolerance = 0x40;
	protected ImageMaskStrategy maskStrategy;
	
	public SubtractiveFilter(int w, int h) {
		this(w, h, 0x40);
	}
	
	public SubtractiveFilter(int w, int h, int tolerance) {
		super(new ColoredPointSearch(w, h));
		this.tolerance = tolerance;
		
		this.searchStrategy = new SoftFloodFillSearch(w, h);
		maskStrategy = new ImageMaskStrategy();
		searchStrategy.setPixelStrategy(maskStrategy);
	}
	
	public SubtractiveFilter(int w, int h, ImageSource source) {
		this(w, h);
		
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
