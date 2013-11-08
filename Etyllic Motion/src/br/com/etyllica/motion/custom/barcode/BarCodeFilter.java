package br.com.etyllica.motion.custom.barcode;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.custom.wand.BlackWandFilter;
import br.com.etyllica.motion.features.Component;

public class BarCodeFilter extends BlackWandFilter{

	public BarCodeFilter(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	protected int findPoints(int i, int j, BufferedImage b, Component component){

		int w = b.getWidth();
		int h = b.getHeight();
		
		if(mask[i][j]){
			return 0;
		}else if (i==border||i==w-border||j==border||j==h-border){
			mask[i][j] = true;
			return 0;
		}


		int offsetX = 0;
		int offsetY = 0;

		//System.out.println("Found: "+i+" "+j);

		mask[i][j] = true;
		component.add(i, j);

		if(validateColor(b.getRGB(i+1, j))/*&&!validateColor(b.getRGB(i+1, j-1))*/){

			offsetX = 1;
			offsetY = 0;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}
		else if(validateColor(b.getRGB(i+1, j+1))){

			offsetX = 1;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}
		
		if(validateColor(b.getRGB(i, j+1))){

			offsetX = 0;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}else if(validateColor(b.getRGB(i-1, j+1))){

			offsetX = -1;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}
		
		
		return component.getPoints().size();

	}
	
}
