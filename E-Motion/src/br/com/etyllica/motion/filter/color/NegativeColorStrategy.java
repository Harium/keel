package br.com.etyllica.motion.filter.color;

import java.awt.Color;

public class NegativeColorStrategy extends ColorStrategy {

	public NegativeColorStrategy() {
		super();
	}
	
	public NegativeColorStrategy(Color color) {
		this(color.getRGB());
	}
	
	public NegativeColorStrategy(Color color, int tolerance) {
		this(color);
		this.tolerance = tolerance;
	}
	
	public NegativeColorStrategy(int color) {
		super();
		this.color = color;
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return !super.validateColor(rgb);
	}
		
}
