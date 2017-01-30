package br.com.etyllica.motion.filter;

import java.util.List;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.mask.ImageMaskStrategy;
import br.com.etyllica.motion.filter.search.ColoredPointSearch;

public class SubtractiveFilter extends TrackingFilter {

	protected ImageMaskStrategy maskStrategy;
		
	public SubtractiveFilter(int w, int h) {
		super(new ColoredPointSearch(w, h));
		
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
		maskStrategy.setTolerance(tolerance);
	}
	
	public ImageSource getMask() {
		return maskStrategy.getMask();
	}

	public void setMask(ImageSource mask) {
		maskStrategy.setMask(mask);
	}

}
