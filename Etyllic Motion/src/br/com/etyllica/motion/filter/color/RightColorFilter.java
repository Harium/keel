package br.com.etyllica.motion.filter.color;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.features.Component;

public class RightColorFilter extends ColorFilter{

	public RightColorFilter(int w, int h) {
		super(w, h);
	}

	public Component filterFirst(BufferedImage bimg, Component component){

		super.setup();
		
		int x = component.getLowestX();
		int y = component.getLowestY();

		int w = component.getW();
		int h = component.getH();

		for(int i=w-border;i>x+border;i--){

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
