package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.SoftComponentFilter;
import br.com.etyllica.motion.core.dynamic.DynamicArrayMask;
import br.com.etyllica.motion.core.dynamic.DynamicMask;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;

public class SoftFloodFillSearch extends SoftComponentFilter {

	private int minNeighbors = 1;

	private int maxNeighbors = 9;
	
	private Component lastComponent;
	
	private DynamicMask mask;
	
	private int UNDEFINED_COLOR = -1;
	
	public SoftFloodFillSearch(int w, int h) {
		super(w, h, new SkinColorStrategy());
		
		mask = new DynamicArrayMask(w, h);
	}

	public SoftFloodFillSearch(int w, int h, int minNeighbors) {
		super(w, h, new SkinColorStrategy());

		mask = new DynamicArrayMask(w, h);
		
		this.minNeighbors = minNeighbors;
	}

	public SoftFloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
		super(w, h, new SkinColorStrategy());

		this.minNeighbors = minNeighbors;

		this.maxNeighbors = maxNeighbors;
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {

		List<Component> list = filter(bimg, component);

		if(!list.isEmpty()) {

			lastComponent = list.get(0);

		}

		return lastComponent;
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		setup();

		int x = border;
		int y = border;
		
		int width = getComponentWidth(component);
		int height = getComponentHeight(component);

		for (int j = y; j < height; j += step) {
			for (int i = x; i < width; i += step) {
				
				//Found some valid pixel
				int rgb = bimg.getRGB(i, j);
				
				if (verifySinglePixel(i, j, rgb, UNDEFINED_COLOR)) {

					//Clear Queue
					Queue<Point2D> queue = new LinkedList<Point2D>();
					Component found = new Component();
					
					Point2D firstPoint = new Point2D(i, j);
					
					addPoint(found, firstPoint);
					addNeighbors(queue, firstPoint, rgb);//Add reference to its color

					//For each validated neighbor
					while (!queue.isEmpty()) {

						//Queue.pop(); 
						Point2D p = queue.remove();
						
						int px = (int)p.getX();
						int py = (int)p.getY();
												
						//Update rgb
						rgb = bimg.getRGB(i, j);
						
						//Get color from previous pixel
						int lastColor = p.getColor();

						if ((px >= x) && (px < width &&
								(py >= y) && (py < height))) {
							
							if (verifyPixel(px, py, rgb, lastColor, bimg)) {
								addPoint(found, p);
								addNeighbors(queue, p, rgb);
							}
						}
					}

					if(this.validate(found)) {
						result.add(componentModifierStrategy.modifyComponent(found));
					}
				}

				mask.setTouched(i, j);
			}
		}

		return result;
	}
	
	private void addPoint(Component component, Point2D p) {
		mask.setTouched((int)p.getX(), (int)p.getY());
		component.add(p);
	}
	
	private void addNeighbors(Queue<Point2D> queue, Point2D p, int lastColor) {
		queue.add(new Point2D(p.getX() + step, p.getY(), lastColor));
		queue.add(new Point2D(p.getX() - step, p.getY(), lastColor));
		queue.add(new Point2D(p.getX(), p.getY() + step, lastColor));
		queue.add(new Point2D(p.getX(), p.getY() - step, lastColor));
	}
	
	private boolean verifyPixel(int px, int py, int rgb, int lastRGB, BufferedImage bimg) {
		
		if (verifySinglePixel(px, py, rgb, lastRGB)) {
			if(minNeighbors > 0 && maxNeighbors > 0) {
				if(!verifyNeighbors(px, py, rgb, bimg)) {
					return false;
				}
			}
			
			return true;			
		}

		return false;

	}

	private boolean verifySinglePixel(int px, int py, int rgb, int lastRGB) {

		if(mask.isUnknown(px, py)) {
			if(pixelStrategy.validateColor(rgb)) {
				mask.setValid(px, py);
			} else if(lastRGB != UNDEFINED_COLOR) {
				if(pixelStrategy.weakValidateColor(lastRGB, rgb)) {
					mask.setValid(px, py);
				}
			} else {			
				mask.setInvalid(px, py);
			}
		}
		
		return (!mask.isTouched(px, py) && mask.isValid(px, py));
	}

	private boolean verifyNeighbors(int px, int py, int rgb, BufferedImage bimg) {

		int verified = 0;
		
		int baseColor = rgb;

		for(int j = py-step; j <= py+step; j += step) {

			for(int i = px-step; i <= px+step; i += step) {

				int currentColor = bimg.getRGB(i, j);
				if(pixelStrategy.weakValidateColor(baseColor, currentColor)) {
					verified++;
				}
				baseColor = currentColor;
			}
		}

		return verified >= minNeighbors && verified <= maxNeighbors;
	}

	public int getMinNeighbors() {
		return minNeighbors;
	}

	public void setMinNeighbors(int minNeighbors) {
		this.minNeighbors = minNeighbors;
	}

	public int getMaxNeighbors() {
		return maxNeighbors;
	}

	public void setMaxNeighbors(int maxNeighbors) {
		this.maxNeighbors = maxNeighbors;
	}	

}
