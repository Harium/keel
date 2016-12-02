package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.feature.Component;

public class RightToLeftSearch extends SearchFilter{

	public Component filterFirst(ImageSource bimg, Component component){

		super.setup();
		
		int x = component.getLowestX();
		int y = component.getLowestY();

		int w = component.getW();
		int h = component.getH();
		
		for(int i=w-border;i>x+border;i--){

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
	public List<Component> filter(ImageSource bimg, Component component) {
		// TODO Auto-generated method stub
		return null;
	}

}
