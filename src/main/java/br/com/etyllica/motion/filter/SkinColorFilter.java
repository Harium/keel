package br.com.etyllica.motion.filter;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.SimpleToleranceStrategy;
import br.com.etyllica.motion.filter.color.skin.SkinColorStrategy;
import br.com.etyllica.motion.filter.search.flood.SoftFloodFillSearch;

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

	public Component filterFirst(BufferedImage bimg, Component component) {
		return searchStrategy.filterFirst(bimg, component);
	}
	
	public List<Component> filter(BufferedImage bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	public void setTolerance(int tolerance) {
		colorStrategy.setTolerance(tolerance);
	}

}
