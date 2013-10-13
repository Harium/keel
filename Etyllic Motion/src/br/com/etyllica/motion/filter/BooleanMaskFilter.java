package br.com.etyllica.motion.filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Componente;

public abstract class BooleanMaskFilter extends ComponentFilter{

	protected boolean[][] mask;
	
	public BooleanMaskFilter(int w, int h){
		super(w,h);
		
		mask = new boolean[w][h];
		
	}
	
	protected void resetMask(){
		
		int w = mask.length; 
		int h = mask[0].length;
		
		for(int j=0;j<h;j++){
			
			for(int i=0;i<w;i++){
				mask[i][j] = false;
			}
			
		}
	}

	public boolean[][] getMask() {
		return mask;
	}
	
	@Override
	public List<Componente> filter(BufferedImage bimg, List<Componente> components) {
			
		List<Componente> filteredComponents = new ArrayList<Componente>();
		
		for(Componente component: components){
			
			resetMask();
			
			filteredComponents.addAll(filter(bimg, component));	
						
		}
		
		return filteredComponents;
	}
	
}
