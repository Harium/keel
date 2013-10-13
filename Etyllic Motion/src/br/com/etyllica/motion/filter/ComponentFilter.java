package br.com.etyllica.motion.filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Componente;

public abstract class ComponentFilter extends FilterImpl{
	
	protected int w;
	protected int h;
	
	public ComponentFilter(int w, int h){
		super();
		
		this.w = w;
		this.h = h;
	}
	
	public List<Componente> filter(BufferedImage bimg, List<Componente> components) {
		
		List<Componente> filteredComponents = new ArrayList<Componente>();
		
		for(Componente component: components){
			
			filteredComponents.addAll(filter(bimg, component));
			
		}
		
		return filteredComponents;
	}
	
	protected abstract List<Componente> filter(BufferedImage bimg, Componente component);

}
