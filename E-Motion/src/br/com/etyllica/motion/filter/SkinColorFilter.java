package br.com.etyllica.motion.filter;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;
import br.com.etyllica.motion.filter.color.ToleranceStrategy;
import br.com.etyllica.motion.filter.search.ColoredPointSearch;

public class SkinColorFilter extends TrackingFilter {

	protected ToleranceStrategy colorStrategy;
		
	public SkinColorFilter(int w, int h) {
		super(new ColoredPointSearch(w, h));		
	}
		
	public SkinColorFilter(int w, int h, int tolerance) {
		this(w, h);
		
		colorStrategy = new SkinColorStrategy(tolerance);
				
		searchStrategy.setPixelStrategy(colorStrategy);
				
	}

	public Component filterFirst(BufferedImage bimg, Component component) {
		
		return searchStrategy.filterFirst(bimg, component);
		
	}
	
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		return searchStrategy.filter(bimg, component);
	}

	public int getTolerance() {
		return colorStrategy.getTolerance();
	}

	public void setTolerance(int tolerance) {
		colorStrategy.setTolerance(tolerance);
	}

}
