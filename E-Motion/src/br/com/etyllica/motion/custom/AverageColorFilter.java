package br.com.etyllica.motion.custom;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ProcessFilter;
import br.com.etyllica.motion.core.ProcessPointsFilter;
import br.com.etyllica.motion.filter.color.ColorStrategy;

public class AverageColorFilter implements ProcessFilter, ProcessPointsFilter {

	private int pixelCount;
	
	private int averageRed;
	private int averageBlue;
	private int averageGreen;
	
	private Color color;
	
	public AverageColorFilter() {
		super();
		
		reset();
	}
	
	private void reset() {
		
		pixelCount = 0;
		
		averageRed = 0;
		averageBlue = 0;
		averageGreen = 0;
		
	}

	public void process(BufferedImage buffer) {

		reset();
						
		for(int j=0; j<buffer.getHeight(); j++) {

			for(int i=0; i<buffer.getWidth(); i++) {
				
				int rgb = buffer.getRGB(i, j);
				
				averageRed += ColorStrategy.getRed(rgb);
				
				averageBlue += ColorStrategy.getBlue(rgb);
				
				averageGreen += ColorStrategy.getGreen(rgb);
				
				pixelCount ++;
				
			}

		}
		
		averageRed /= pixelCount;
		
		averageBlue /= pixelCount;
		
		averageGreen /= pixelCount;
		
		this.color = new Color(averageRed, averageGreen, averageBlue);

	}

	public int getAverageRed() {
		return averageRed;
	}

	public int getAverageBlue() {
		return averageBlue;
	}

	public int getAverageGreen() {
		return averageGreen;
	}

	public int getPixelCount() {
		return pixelCount;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void process(BufferedImage buffer, List<Point2D> points) {
		
		reset();
		
		for(Point2D point: points) {
			
			int i = (int)point.getX();
			
			int j = (int)point.getY();
			
			int rgb = buffer.getRGB(i, j);
			
			averageRed += ColorStrategy.getRed(rgb);
			
			averageBlue += ColorStrategy.getBlue(rgb);
			
			averageGreen += ColorStrategy.getGreen(rgb);
			
			pixelCount ++;
			
		}
			
		averageRed /= pixelCount;
		
		averageBlue /= pixelCount;
		
		averageGreen /= pixelCount;
		
		this.color = new Color(averageRed, averageGreen, averageBlue);
		
	}
		
}
