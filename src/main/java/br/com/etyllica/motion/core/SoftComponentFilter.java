package br.com.etyllica.motion.core;

import br.com.etyllica.motion.core.strategy.SoftSearchFilter;
import br.com.etyllica.motion.filter.SoftPixelStrategy;


public abstract class SoftComponentFilter extends SoftSearchFilter {
	
	protected int w;
	
	protected int h;
	
	public SoftComponentFilter(int w, int h) {
		super();
		
		this.w = w;
		this.h = h;
	}
	
	public SoftComponentFilter(int w, int h, SoftPixelStrategy colorStrategy) {
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
