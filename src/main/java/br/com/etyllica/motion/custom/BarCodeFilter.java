package br.com.etyllica.motion.custom;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.flood.FloodFillSearch;
import br.com.etyllica.motion.modifier.EnvelopeModifier;

public class BarCodeFilter extends CustomFilter {
	
	private FloodFillSearch searchStrategy;
	
	public BarCodeFilter(int w, int h){
		super();
		
		colorStrategy = new ColorStrategy(Color.BLACK);
				
		modifierStrategy = new EnvelopeModifier();
		

		searchStrategy = new FloodFillSearch(w, h);
		
		searchStrategy.setPixelStrategy(colorStrategy);
				
		searchStrategy.setComponentModifierStrategy(modifierStrategy);
		
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {
		
		return searchStrategy.filterFirst(bimg, component);
		
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub	
	}
	
}
