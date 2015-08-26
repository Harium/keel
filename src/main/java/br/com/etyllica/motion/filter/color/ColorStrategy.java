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
		
		setTolerance(tolerance);
	}
	
	public ColorStrategy(Color color, int maxTolerance, int minTolerance) {
		this(color);
		
		setTolerance(maxTolerance, minTolerance);
	}
	
	public ColorStrategy(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
		this(color);
		
		setTolerance(redTolerance, greenTolerance, blueTolerance);
	}
	
	public ColorStrategy(int color) {
		super();
		setColor(color);
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, this.color, minToleranceRed, maxToleranceRed, minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
	}
	
	@Override
	public boolean strongValidateColor(int rgb, int reference) {
		int weakFactor = 2;
		return isColor(rgb, reference, minToleranceRed/weakFactor, maxToleranceRed/weakFactor, minToleranceGreen/weakFactor, maxToleranceGreen/weakFactor, minToleranceBlue/weakFactor, maxToleranceBlue/weakFactor);
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
				
		return isColor(rgb, color, minTolerance, maxTolerance, minTolerance, maxTolerance, minTolerance, maxTolerance);
		
	}
	
	public static boolean isColor(int rgb, int color, int minToleranceRed, int maxToleranceRed, int minToleranceGreen, int maxToleranceGreen, int minToleranceBlue, int maxToleranceBlue) {
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		int cr = getRed(color);
		int cg = getGreen(color);
		int cb = getBlue(color);

		if((r>=cr-minToleranceRed)&&(r<=cr+maxToleranceRed)&&
			(g>=cg-minToleranceGreen)&&(g<=cg+maxToleranceGreen)&&
			(b>=cb-minToleranceBlue)&&(b<=cb+maxToleranceBlue)) {
			return true;
		}
		
		return false;
		
	}
	
	public static boolean isDarkerColor(int rgb, int color, int tolerance) {
		return isDarkerColor(rgb, color, tolerance, tolerance, tolerance);
	}
	
	public static boolean isDarkerColor(int rgb, int color, int minToleranceRed, int minToleranceGreen, int minToleranceBlue) {
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		int cr = getRed(color);
		int cg = getGreen(color);
		int cb = getBlue(color);

		if((r<=cr)&&(r>=cr-minToleranceRed)&&
			(g<=cg)&&(g>=cg-minToleranceGreen)&&
			(b<=cb)&&(b>=cb-minToleranceBlue)) {
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
	
	public static int getRGB(int red, int green, int blue) {
		
		int rgb = red;
		rgb = (rgb << 8) + green;
		rgb = (rgb << 8) + blue;
		
		return rgb;
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
