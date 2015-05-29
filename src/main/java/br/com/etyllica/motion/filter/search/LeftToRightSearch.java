package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.feature.Component;

public class LeftToRightSearch extends SearchFilter {

	public Component filterFirst(BufferedImage bimg, Component component) {

		super.setup();
				
		int x = component.getLowestX();
		int y = component.getLowestY();

		int w = component.getW();
		int h = component.getH();
		
		for(int i=x+border;i<w-border;i++){

			for(int j=y+border;j<h-border;j++){

				if(pixelStrategy.validateColor(bimg.getRGB(i, j))){

					lastComponent.setLocation(i, j);

					return lastComponent;

				}

			}

		}

		return lastComponent;

	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		// TODO Auto-generated method stub
		return null;
	}

}
