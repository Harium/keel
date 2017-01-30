package br.com.etyllica.motion.filter.search;

import java.util.List;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.feature.Component;

public class RightToLeftSearch extends SearchFilter{

	public Component filterFirst(ImageSource bimg, Component component){
		super.setup(component.getW(), component.getH());
		
		int x = component.getLowestX();
		int y = component.getLowestY();

		int w = component.getW();
		int h = component.getH();
		
		//TODO Swap i,j
		for(int i=w-border;i>x+border;i-=step){

			for(int j=y+border;j<h-border;j+=step){

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
