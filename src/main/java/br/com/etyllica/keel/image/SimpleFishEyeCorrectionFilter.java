package br.com.etyllica.keel.image;

import java.awt.image.BufferedImage;

/**
 * Original ideas found at: http://www.tannerhelland.com/4743/simple-algorithm-correcting-lens-distortion/
 *
 */

public class SimpleFishEyeCorrectionFilter implements ImageProcessor {

	private double strength = 1f;
	
	private double zoom = 1;

	public SimpleFishEyeCorrectionFilter() {
		super();
	}
	
	public SimpleFishEyeCorrectionFilter(double strength) {
		super();
		this.strength = strength;
	}
	
	public SimpleFishEyeCorrectionFilter(double strength, double zoom) {
		super();
		this.strength = strength;
		this.zoom = zoom;
	}
	
	@Override
	public BufferedImage process(BufferedImage image) {

		int w = image.getWidth();

		int h = image.getHeight();

		int halfWidth = w/2;

		int halfHeight = h/2;

		double correctionRadius = Math.sqrt(w*w + h*h) / strength;

		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		for(int j=0; j<h; j++) {

			for(int i=0; i<w; i++) {

				int newX = i - halfWidth;
				int newY = j - halfHeight;

				double distance = Math.sqrt(newX*newX + newY*newY);
				double r = distance / correctionRadius;

				double theta = 1;

				if (r != 0) {
					theta = Math.atan(r)/r;
				}

				double sourceX = halfWidth + theta * newX * zoom;
				double sourceY = halfHeight + theta * newY * zoom;

				int rgb = image.getRGB(i, j);
				
				if(sourceX>=w||sourceY>=h) {
					System.out.println("Problem here!");
				}
				
				output.setRGB((int)sourceX, (int)sourceY, rgb);
			}
		}

		return output;
	}

}
