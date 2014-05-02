package br.com.etyllica.motion.filter.image;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.filter.color.ColorStrategy;

/**
 * Original ideas found at: http://www.tannerhelland.com/3643/grayscale-image-algorithm-vb6/
 *
 */

public class BlackAndWhiteQuickProcessor implements ImageProcessor {

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
				
				int gray = (red+green+blue)/3;
				
				rgb = ColorStrategy.getRGB(gray, gray, gray);
								
				output.setRGB(i, j, rgb);
				
			}
			
		}
		
		return output;
	}
	
	
	
	
}
