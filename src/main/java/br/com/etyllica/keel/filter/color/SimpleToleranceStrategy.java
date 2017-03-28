package br.com.etyllica.keel.filter.color;

import br.com.etyllica.keel.core.strategy.PixelStrategy;

public abstract class SimpleToleranceStrategy implements PixelStrategy {

	protected int tolerance = 0x42;
	
	public SimpleToleranceStrategy() {
		super();
	}
	
	public SimpleToleranceStrategy(int tolerance) {
		super();
		
		this.tolerance = tolerance;
	}

	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}
	
	@Override
	public boolean strongValidateColor(int rgb, int j, int i, int reference) {
		return validateColor(rgb, j, i);
	}
		
}
