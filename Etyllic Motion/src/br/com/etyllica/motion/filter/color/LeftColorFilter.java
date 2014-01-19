package br.com.etyllica.motion.filter.color;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.features.Component;

public class LeftColorFilter extends ColorFilter{

	public LeftColorFilter() {
		super();
	}

	public Component filterFirst(BufferedImage bimg, Component component){

		super.setup();
		
		int x = component.getLowestX();
		int y = component.getLowestY();

		int w = component.getW();
		int h = component.getH();

		for(int i=x+border;i<w-border;i++){

			for(int j=y+border;j<h-border;j++){

				if(validateColor(bimg.getRGB(i, j))){

					lastComponent.setLocation(i, j);

					return lastComponent;

				}

			}

		}

		return lastComponent;

	}

}
