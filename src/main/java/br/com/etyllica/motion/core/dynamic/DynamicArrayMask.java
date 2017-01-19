package br.com.etyllica.motion.core.dynamic;

import br.com.etyllica.motion.filter.dynamic.DynamicPixel;


public class DynamicArrayMask implements DynamicMask {

	private int w;
	private int h;
	protected int[][] mask;
		
	public DynamicArrayMask(int w, int h) {
		super();
		
		this.w = w;
		this.h = h;
		
		mask = new int[w][h];
		
		reset();
	}
		
	public void reset() {
		
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

	@Override
	public int getW() {
		return w;
	}

	@Override
	public int getH() {
		return h;
	}
	
}
