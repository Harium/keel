package br.com.etyllica.motion.filter;

import br.com.etyllica.motion.features.Componente;

public abstract class FilterImpl implements Filter{

	protected int step = 1;
	protected int border = 0;
	
	protected boolean isSkin(int rgb){

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		int difG = r-g;
		int difB = r-b;
		
		boolean rok = r>20;
		boolean gok = difG>=0&&difG<=70&&g<0xba;
		boolean bok = difB>=0&&difB<=95&&b<0xba;
		
		//184
		//117
		//91
				
		if(rok&&gok&&bok){
			return true;
		}
		
		return false;
	}

	protected boolean isBlack(int rgb){

		int black = 0x49;

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		if((r<black)&&(g<black)&&(b<black)){
			return true;
		}
		return false;
	}

	protected int difMod(int a, int b){
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
	
	protected boolean validateColor(int rgb){
		return true;
	}
	
	protected boolean validateComponent(Componente component){
		
		return true;
	}
	
}
