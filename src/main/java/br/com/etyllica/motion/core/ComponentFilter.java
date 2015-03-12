package br.com.etyllica.motion.core;

import br.com.etyllica.motion.core.strategy.PixelStrategy;
import br.com.etyllica.motion.core.strategy.SearchFilter;


public abstract class ComponentFilter extends SearchFilter {
	
	protected int w;
	
	protected int h;
	
	public ComponentFilter(int w, int h){
		super();
		
		this.w = w;
		this.h = h;
	}
	
	public ComponentFilter(int w, int h, PixelStrategy colorStrategy){
		super(colorStrategy);
		
		this.w = w;
		this.h = h;
	}
	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
}
