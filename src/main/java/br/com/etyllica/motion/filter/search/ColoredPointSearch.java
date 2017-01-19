package br.com.etyllica.motion.filter.search;

import java.awt.Color;
import java.util.List;

import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;

public class ColoredPointSearch extends BooleanMaskSearch {

	public ColoredPointSearch(int w, int h) {
		super(w, h);
	}
	
	public ColoredPointSearch(int w, int h, Color color) {
		super(w, h, new ColorStrategy(color.getRGB()));
	}

	@Override
	public Component filterFirst(ImageSource bimg, Component component) {
		super.setup(component.getW(), component.getH());
										
		int x = component.getLowestX()+border;
		int y = component.getLowestY()+border;
		
		int w = component.getW()-border;
		int h = component.getH()-border;
		
		for(int j=y;j<h;j+=step) {
			
			for(int i=x;i<w;i+=step) {
				
				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j))) {
					
					lastComponent.setBounds(i, j, 1, 1);
										
					return lastComponent;

				}

			}

		}

		return lastComponent;
		
	}
	
	@Override
	public List<Component> filter(ImageSource bimg, Component component) {
		super.setup(component.getW(), component.getH());
		
		int x = component.getLowestX()+border;
		int y = component.getLowestY()+border;
		
		int w = component.getW()-border;
		int h = component.getH()-border;
		
		for(int j=y;j<h;j+=step) {
			
			for(int i=x;i<w;i+=step) {

				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j))) {
					
					Component holder = new Component(i, j, 1, 1);
					
					result.add(holder);

					return result;
				}

			}

		}
		
		return result;
	}
			
}
