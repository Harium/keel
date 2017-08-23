package br.com.etyllica.keel.custom;

import java.util.List;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.flood.FloodFillSearch;
import br.com.etyllica.keel.modifier.EnvelopeModifier;
import com.harium.etyl.commons.graphics.Color;

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
	public Component filterFirst(ImageSource bimg, Component component) {
		
		return searchStrategy.filterFirst(bimg, component);
		
	}

	@Override
	public List<Component> filter(ImageSource bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	@Override
	public void setup(int w, int h) {
		// TODO Auto-generated method stub	
	}
	
}
