package br.com.etyllica.motion.filter.color;

import br.com.etyllica.motion.core.strategy.PixelStrategy;

public abstract class ToleranceStrategy implements PixelStrategy {

	protected int maxToleranceRed = 0x42;
	
	protected int maxToleranceGreen = 0x42;
	
	protected int maxToleranceBlue = 0x42;
	
	protected int minToleranceRed = 0x42;
	
	protected int minToleranceGreen = 0x42;
	
	protected int minToleranceBlue = 0x42;
	
	public ToleranceStrategy() {
		super();
	}
	
	public ToleranceStrategy(int tolerance) {
		super();
		
		setTolerance(tolerance);
	}
	
	public ToleranceStrategy(int maxTolerance, int minTolerance) {
		super();
		
		setTolerance(maxTolerance, minTolerance);
	}
	
	public void setTolerance(int tolerance) {
		setTolerance(tolerance, tolerance, tolerance);	
	}
	
	public void setTolerance(int maxTolerance, int minTolerance) {
		setTolerance(maxTolerance, maxTolerance, maxTolerance, minTolerance, minTolerance, minTolerance);
	}
	
	public void setTolerance(int redTolerance, int greenTolerance, int blueTolerance) {
		setTolerance(redTolerance, greenTolerance, blueTolerance, redTolerance, greenTolerance, blueTolerance);
	}
	
	public void setTolerance(int redMaxTolerance, int greenMaxTolerance, int blueMaxTolerance, int redMinTolerance, int greenMinTolerance, int blueMinTolerance) {
		
		maxToleranceRed = redMaxTolerance;
		maxToleranceGreen = greenMaxTolerance;
		maxToleranceBlue = blueMaxTolerance;
		
		minToleranceRed = redMinTolerance;
		minToleranceGreen = greenMinTolerance;
		minToleranceBlue = blueMinTolerance;
	}
	
	public void setOffsetTolerance(int tolerance) {
		
		maxToleranceRed += tolerance;
		maxToleranceGreen += tolerance;
		maxToleranceBlue += tolerance;
		
		minToleranceRed += tolerance;
		minToleranceGreen += tolerance;
		minToleranceBlue += tolerance;
		
	}

	public int getMaxToleranceRed() {
		return maxToleranceRed;
	}

	public void setMaxToleranceRed(int maxToleranceRed) {
		this.maxToleranceRed = maxToleranceRed;
	}

	public int getMaxToleranceGreen() {
		return maxToleranceGreen;
	}

	public void setMaxToleranceGreen(int maxToleranceGreen) {
		this.maxToleranceGreen = maxToleranceGreen;
	}

	public int getMaxToleranceBlue() {
		return maxToleranceBlue;
	}

	public void setMaxToleranceBlue(int maxToleranceBlue) {
		this.maxToleranceBlue = maxToleranceBlue;
	}

	public int getMinToleranceRed() {
		return minToleranceRed;
	}

	public void setMinToleranceRed(int minToleranceRed) {
		this.minToleranceRed = minToleranceRed;
	}

	public int getMinToleranceGreen() {
		return minToleranceGreen;
	}

	public void setMinToleranceGreen(int minToleranceGreen) {
		this.minToleranceGreen = minToleranceGreen;
	}

	public int getMinToleranceBlue() {
		return minToleranceBlue;
	}

	public void setMinToleranceBlue(int minToleranceBlue) {
		this.minToleranceBlue = minToleranceBlue;
	}
	
	@Override
	public boolean strongValidateColor(int rgb, int reference) {
		return validateColor(rgb);
	}
}
