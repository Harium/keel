package br.com.etyllica.keel.filter.color;

import java.awt.Color;

public class NegativeColorStrategy extends ColorStrategy {

	public NegativeColorStrategy() {
		super();
	}
	
	public NegativeColorStrategy(Color color) {
		super(color.getRGB());
	}
	
	public NegativeColorStrategy(Color color, int tolerance) {
		super(color, tolerance);
	}
	
	public NegativeColorStrategy(int color) {
		super();
		this.color = color;
	}
	
	@Override
	public boolean validateColor(int rgb, int j, int i) {
		return !super.validateColor(rgb, j, i);
	}
		
}
