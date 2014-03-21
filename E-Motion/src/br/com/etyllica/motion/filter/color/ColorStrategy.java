package br.com.etyllica.motion.filter.color;

import java.awt.Color;

public class ColorStrategy extends ToleranceStrategy {

	protected int color = Color.BLACK.getRGB();
	
	public ColorStrategy() {
		super();
	}
	
	public ColorStrategy(Color color) {
		this(color.getRGB());
	}
	
	public ColorStrategy(Color color, int tolerance) {
		this(color);
		this.tolerance = tolerance;
	}
	
	public ColorStrategy(int color) {
		super();
		this.color = color;
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, this.color, tolerance);
	}
	
	public static boolean isColor(int rgb, int color) {
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		if((r==getRed(color))&&(g==getGreen(color))&&(b==getBlue(color))) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isColor(int rgb, int color, int tolerance) {
				
		return isColor(rgb, color, tolerance, tolerance);
		
	}
	
	public static boolean isColor(int rgb, int color, int minTolerance, int maxTolerance) {
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		int cr = getRed(color);
		int cg = getGreen(color);
		int cb = getBlue(color);

		if((r>=cr-minTolerance)&&(r<=cr+maxTolerance)&&
			(g>=cg-minTolerance)&&(g<=cg+maxTolerance)&&
			(b>=cb-minTolerance)&&(b<=cb+maxTolerance)) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isDarkerColor(int rgb, int color, int tolerance) {
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		int cr = getRed(color);
		int cg = getGreen(color);
		int cb = getBlue(color);

		if((r<=cr)&&(r>=cr-tolerance)&&
			(g<=cg)&&(g>=cg-tolerance)&&
			(b<=cb)&&(b>=cb-tolerance)) {
			return true;
		}
		
		return false;
		
	}
	
	public static int getRed(int rgb) {

		return (rgb >> 16) & 0xFF;

	}

	public static int getGreen(int rgb) {

		return (rgb >> 8) & 0xFF;

	}

	public static int getBlue(int rgb) {

		return rgb & 0xFF;
		
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color.getRGB();
	}
	
}
