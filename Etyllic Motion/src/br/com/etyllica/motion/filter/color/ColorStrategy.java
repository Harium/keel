package br.com.etyllica.motion.filter.color;

import java.awt.Color;

public class ColorStrategy extends ToleranceStrategy {

	protected int color = Color.BLACK.getRGB();
	
	public ColorStrategy(){
		super();
	}
	
	public ColorStrategy(Color color){
		super();
		this.color = color.getRGB();
	}
	
	public ColorStrategy(int color){
		super();
		this.color = color;
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, this.color, tolerance);
	}
	
	public boolean isColor(int rgb, int color){
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		if((r==getRed(color))&&(g==getGreen(color))&&(b==getBlue(color))){
			return true;
		}
		return false;
		
	}
	
	public boolean isColor(int rgb, int color, int tolerance){
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		int cr = getRed(color);
		int cg = getGreen(color);
		int cb = getBlue(color);

		if((r>=cr-tolerance)&&(r<=cr+tolerance)&&
			(g>=cg-tolerance)&&(g<=cg+tolerance)&&
			(b>=cb-tolerance)&&(b<=cb+tolerance)){
			return true;
		}
		return false;
		
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;	
	}
	
}
