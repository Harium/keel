package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.core.features.Component;

public class PointSearch extends BooleanMaskSearch {

	public PointSearch(int w, int h) {
		super(w, h);
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {
		
		super.setup();
		
		lastComponent.reset();
								
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
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		super.setup();
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		int i,j;

		for(j=border;j<h-border;j+=step) {

			for(i=border;i<w-border;i+=step) {

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
