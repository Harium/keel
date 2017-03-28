package br.com.etyllica.keel.filter.search;

import java.util.List;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.core.strategy.SearchFilter;
import br.com.etyllica.keel.feature.Component;

public class DumbSearch extends SearchFilter {

	public Component filterFirst(ImageSource bimg, Component component) {
		super.setup(component.getW(), component.getH());

		int w = component.getW();
		int h = component.getH();


		//Swap i,j
		for(int j=border;j<h-border;j++){

			for(int i=border;i<w-border;i++){

				if(pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)){

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
