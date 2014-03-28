package br.com.etyllica.motion.processor;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.filter.color.ColorStrategy;

public class ContrastQuickProcessor implements ImageProcessor {

	private int contrast = 10;
	
	public ContrastQuickProcessor() {
		super();
	}
	
	public ContrastQuickProcessor(int contrast) {
		super();
		
		this.contrast = contrast;
	}
		
	@Override
	public BufferedImage process(BufferedImage image) {
		
		int w = image.getWidth();
		
		int h = image.getHeight();
		
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for(int j=0; j<h; j++) {
			
			for(int i=0; i<w; i++) {
				
				int rgb = image.getRGB(i, j);
				
				int red = ColorStrategy.getRed(rgb);
				
				int green = ColorStrategy.getGreen(rgb);
				
				int blue = ColorStrategy.getBlue(rgb);
				
				int average = (red+green+blue)/3;
				
				//Bright pixel
				if(average>128) {
				
					red = brighterPixel(red);				
					
					green = brighterPixel(green);
					
					blue = brighterPixel(blue);
					
				} else {
					
					red = darkerPixel(red);
					
					green = darkerPixel(green);
					
					blue = darkerPixel(blue);
					
				}
				
				rgb = ColorStrategy.getRGB(red, green, blue);
								
				output.setRGB(i, j, rgb);
				
			}
			
		}
		
		return output;
	}
	
	private int brighterPixel(int pixel) {
		
		if(pixel<255-contrast) {
			return pixel+contrast;
		} else {
			return 255;
		}
		
	}
	
	private int darkerPixel(int pixel) {
		
		if(pixel>contrast) {
			return pixel-contrast;
		} else {
			return 0;
		}
		
	}
	
}
