package br.com.etyllica.motion.filter.color;

import br.com.etyllica.motion.core.strategy.PixelStrategy;

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
	public boolean weakValidateColor(int rgb, int reference) {
		return validateColor(rgb);
	}
		
}
