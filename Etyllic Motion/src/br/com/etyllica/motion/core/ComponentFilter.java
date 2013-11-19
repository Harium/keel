package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Component;

public abstract class ComponentFilter extends FilterImpl{
	
	protected int w;
	protected int h;
	
	public ComponentFilter(int w, int h){
		super();
		
		this.w = w;
		this.h = h;
	}
	
	public List<Component> filter(BufferedImage bimg, List<Component> components) {
		
		List<Component> filteredComponents = new ArrayList<Component>();
		
		for(Component component: components){
			
			filteredComponents.addAll(filter(bimg, component));
			
		}
		
		return filteredComponents;
	}
	
	protected abstract List<Component> filter(BufferedImage bimg, Component component);

}
