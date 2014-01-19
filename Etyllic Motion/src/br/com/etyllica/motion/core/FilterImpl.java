package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Component;


public abstract class FilterImpl implements Filter{

	protected int step = 1;
	
	protected int border = 0;
	
	protected List<Component> result = new ArrayList<Component>();

	public static boolean isSkin(int rgb){

		return isSkin(rgb, 0);

	}
	
	public static boolean isSkin(int rgb, int tolerance){

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		float x = r;
		float y = b+(g-b);

		float maxTolerance = tolerance;
		float minTolerance = tolerance;
		
		float my=(8*x)/9-40/9;

		if(x>105&&x<175){
			minTolerance = tolerance*1.3f;
		}

		return x>40&&x<230&&(y>my-minTolerance&&y<my+maxTolerance);

	}

	public static boolean isColor(int rgb, int color){
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		if((r==getRed(color))&&(g==getGreen(color))&&(b==getBlue(color))){
			return true;
		}
		return false;
		
	}
	
	public static boolean isColor(int rgb, int color, int tolerance){
		
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
	
	public static boolean isBlack(int rgb){

		int black = 0x49;

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		if((r<black)&&(g<black)&&(b<black)){
			return true;
		}
		return false;
	}

	public static int difMod(int a, int b){
		if(a>b){
			return a-b;
		}else{
			return b-a;
		}
	}

	public static int getRed(int rgb){

		return (rgb >> 16) & 0xFF;

	}

	public static int getGreen(int rgb){

		//byte green = (byte) ((rgb & 0x0000ff00) >> 8);

		return (rgb >> 8) & 0xFF;

	}

	public static int getBlue(int rgb){
		//byte blue = (byte) (rgb & 0x000000ff);

		return rgb & 0xFF;
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {
		return filter(bimg, component).get(0);
	}

	@Override
	public void setup(){
		result.clear();	
	}
	
}
