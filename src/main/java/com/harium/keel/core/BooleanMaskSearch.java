package com.harium.keel.core;

import com.harium.keel.core.strategy.PixelStrategy;


public abstract class BooleanMaskSearch extends ComponentFilter {

	protected boolean[][] mask;
		
	public BooleanMaskSearch(int w, int h) {
		super(w, h);
		
		resetMask(w, h);
	}
	
	public BooleanMaskSearch(int w, int h, PixelStrategy pixelStrategy) {
		super(w, h, pixelStrategy);
		
		resetMask(w, h);
		
	}
	
	@Override
	public void setW(int w) {
		this.w = w;
		
		resetMask(w,h);
	}

	@Override
	public void setH(int h) {
		this.h = h;
		
		resetMask(w,h);
	}
	
	public void resetMask(int w, int h) {
		
		mask = new boolean[w][h];
		
		resetAllMask();
		
	}
	
	protected void resetAllMask() {
		
		int w = mask.length;
		int h = mask[0].length;
		
		updateMask(0, 0, w, h, false);
				
	}
	
	public void updateMask(int i, int j, int w, int h, boolean update) {
		
		for(int nj=j; nj<j+h; nj++){
			
			for(int ni=i; ni<i+w; ni++){
				mask[ni][nj] = update;
			}
			
		}
		
	}

	public boolean[][] getMask() {
		return mask;
	}

	@Override
	public void setup(int w, int h) {
		super.setup(w, h);
		
		resetAllMask();
	}
	
}
