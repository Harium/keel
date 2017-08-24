package com.harium.keel.filter.search;

import java.util.List;

import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;

public class PointSearch extends BooleanMaskSearch {

	public PointSearch(int w, int h) {
		super(w, h);
	}

	@Override
	public Component filterFirst(ImageSource bimg, Component component) {
		super.setup(component.getW(), component.getH());
		
		lastComponent.reset();
								
		int x = component.getLowestX()+border;
		int y = component.getLowestY()+border;
		
		int w = component.getW()-border;
		int h = component.getH()-border;
		
		//TODO Swap i,j
		for(int j=y;j<h;j+=step) {
			
			for(int i=x;i<w;i+=step) {
				
				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)) {
					
					lastComponent.setBounds(i, j, 1, 1);
										
					return lastComponent;

				}

			}

		}

		return lastComponent;
		
	}
	
	@Override
	public List<Component> filter(ImageSource bimg, Component component) {
		super.setup(component.getW(), component.getH());
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		int i,j;

		//TODO Swap i,j
		for(j=border;j<h-border;j+=step) {

			for(i=border;i<w-border;i+=step) {

				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)) {
					
					Component holder = new Component(i, j, 1, 1);
					
					result.add(holder);

					return result;
				}

			}

		}
		
		return result;
	}
			
}