package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.dumb.DumbComponentFilter;

public class ColorFilter extends BooleanMaskSearch {

	public ColorFilter(int w, int h) {
		super(w, h);
	}
	
	public ColorFilter(int w, int h, Color color) {
		super(w, h, new ColorStrategy(color.getRGB()), new DumbComponentFilter());
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {
		
		super.setup();
		
		Component lastComponent = new BoundingComponent(border, border, w-border, h-border);
								
		int x = component.getLowestX()+border;
		int y = component.getLowestY()+border;
		
		int w = component.getW()-border;
		int h = component.getH()-border;
		
		for(int j=y;j<h;j+=step) {
			
			for(int i=x;i<w;i+=step) {
				
				if(!mask[i][j]&&colorStrategy.validateColor(bimg.getRGB(i, j))) {
					
					lastComponent.setBounds(i, j, 1, 1);
										
					return lastComponent;

				}

			}

		}

		return lastComponent;
		
	}
	
	/**
	 * Returns a single component with all desired pixels 
	 */
	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		super.setup();
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		int i,j;
		
		Component holder = new BoundingComponent(w, h);

		for(j=border;j<h-border;j+=step) {

			for(i=border;i<w-border;i+=step) {

				if(!mask[i][j]&&colorStrategy.validateColor(bimg.getRGB(i, j))) {
					
					holder.add(i, j);
					
				}

			}

		}
		
		result.add(holder);
		
		return result;
	}
			
}
