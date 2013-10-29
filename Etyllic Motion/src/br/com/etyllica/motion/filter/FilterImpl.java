package br.com.etyllica.motion.filter;


public abstract class FilterImpl implements Filter{

	protected int step = 1;
	
	protected int border = 0;

	public boolean isSkin(int rgb){

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		int x = r;
		int y = b+(g-b);

		int maxTolerance = 15;
		int minTolerance = maxTolerance;

		int my=(8*x)/9-40/9;

		if(x>105&&x<175){
			minTolerance = 40;
		}

		return x>40&&x<230&&(y>my-minTolerance&&y<my+maxTolerance);

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
	
	public boolean isBlack(int rgb){

		int black = 0x49;

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		if((r<black)&&(g<black)&&(b<black)){
			return true;
		}
		return false;
	}

	public int difMod(int a, int b){
		if(a>b){
			return a-b;
		}else{
			return b-a;
		}
	}

	public int getRed(int rgb){

		return (rgb >> 16) & 0xFF;

	}

	public int getGreen(int rgb){

		//byte green = (byte) ((rgb & 0x0000ff00) >> 8);

		return (rgb >> 8) & 0xFF;

	}

	public int getBlue(int rgb){
		//byte blue = (byte) (rgb & 0x000000ff);

		return rgb & 0xFF;
	}

}
