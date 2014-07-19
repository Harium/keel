package br.com.etyllica.motion.core.dynamic;

import br.com.etyllica.motion.core.ComponentFilter;
import br.com.etyllica.motion.core.strategy.PixelStrategy;
import br.com.etyllica.motion.filter.dynamic.DynamicPixel;


public abstract class DynamicMaskSearch extends ComponentFilter implements DynamicSearch {

	protected int[][] mask;
		
	public DynamicMaskSearch(int w, int h) {
		super(w, h);
		
		resetMask(w, h);
	}
	
	public DynamicMaskSearch(int w, int h, PixelStrategy pixelStrategy) {
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
		
		mask = new int[w][h];
		
		resetAllMask();
		
	}
	
	protected void resetAllMask() {
		
		int w = mask.length;
		int h = mask[0].length;
		
		updateMask(0, 0, w, h, DynamicPixel.UNKNOWN);
				
	}
	
	public void updateMask(int i, int j, int w, int h, int status) {
		
		for(int nj=j; nj<j+h; nj++){
			
			for(int ni=i; ni<i+w; ni++){
				mask[ni][nj] = status;
			}
			
		}
		
	}

	public int[][] getMask() {
		return mask;
	}

	@Override
	public void setup() {
		
		super.setup();
		
		resetAllMask();
		
	}

	@Override
	public boolean isUnknown(int px, int py) {
		
		int status = mask[px][py];
		
		return DynamicPixel.isUnknown(status);		
	}

	@Override
	public boolean isValid(int px, int py) {
		
		int status = mask[px][py];
		
		return DynamicPixel.isValid(status);
	}

	@Override
	public boolean isTouched(int px, int py) {
		
		int status = mask[px][py];
		
		return DynamicPixel.isTouched(status);		
	}

	@Override
	public void setValid(int px, int py) {
		
		int status = mask[px][py];
		
		mask[px][py] = DynamicPixel.setValid(status);
	}

	@Override
	public void setInvalid(int px, int py) {
		
		int status = mask[px][py];
		
		mask[px][py] = DynamicPixel.setInvalid(status);
	}

	@Override
	public void setTouched(int px, int py) {
		
		int status = mask[px][py];
		
		mask[px][py] = DynamicPixel.setTouched(status);
	}
	
}
