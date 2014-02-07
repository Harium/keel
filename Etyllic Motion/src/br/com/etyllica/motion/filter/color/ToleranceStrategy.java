package br.com.etyllica.motion.filter.color;

import br.com.etyllica.motion.core.strategy.ColorValidatorStrategy;

public abstract class ToleranceStrategy implements ColorValidatorStrategy{

	protected int tolerance = 0x40;

	public ToleranceStrategy() {
		super();
	}
	
	public ToleranceStrategy(int tolerance) {
		super();
		
		this.tolerance = tolerance;
	}
	
	protected int getRed(int rgb){

		return (rgb >> 16) & 0xFF;

	}

	protected int getGreen(int rgb){

		return (rgb >> 8) & 0xFF;

	}

	protected int getBlue(int rgb){

		return rgb & 0xFF;
		
	}
	
	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}
	
	public void setOffsetTolerance(int tolerance) {
		this.tolerance += tolerance;
	}
}
