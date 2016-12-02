package br.com.etyllica.motion.filter.search;

import java.util.List;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.feature.Component;

public class DumbSearch extends SearchFilter {

	public Component filterFirst(ImageSource bimg, Component component) {

		super.setup();

		int w = component.getW();
		int h = component.getH();
		
		for(int j=border;j<h-border;j++){
		
		for(int i=border;i<w-border;i++){

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
