package br.com.etyllica.motion.filter.color;

import java.awt.image.BufferedImage;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.Component;

public class RightColorFilter extends ColorFilter{

	public RightColorFilter() {
		super();
	}

	public Point2D filterFirst(BufferedImage bimg, Component component){

		int x = component.getLowestX();
		int y = component.getLowestY();

		int w = component.getW();
		int h = component.getH();

		for(int i=w-border;i>x+border;i--){

			for(int j=y+border;j<h-border;j++){

				if(validateColor(bimg.getRGB(i, j))){

					lastPoint.setLocation(i, j);

					return lastPoint;

				}

			}

		}

		return lastPoint;

	}

}
