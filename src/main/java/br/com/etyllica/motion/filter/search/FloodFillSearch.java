package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ComponentFilter;
import br.com.etyllica.motion.core.dynamic.DynamicArrayMask;
import br.com.etyllica.motion.core.dynamic.DynamicMask;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;

public class FloodFillSearch extends ComponentFilter {

	protected int minNeighbors = 1;

	protected int maxNeighbors = 9;

	private Component lastComponent;

	protected DynamicMask mask;

	public FloodFillSearch(int w, int h) {
		super(w, h, new SkinColorStrategy());

		mask = new DynamicArrayMask(w, h);
	}

	public FloodFillSearch(int w, int h, int minNeighbors) {
		super(w, h, new SkinColorStrategy());

		mask = new DynamicArrayMask(w, h);

		this.minNeighbors = minNeighbors;
	}

	public FloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
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
	public void setup() {
		super.setup();
		mask.reset();
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		setup();

		int x = border;
		int y = border;

		int width = getComponentWidth(component);
		int height = getComponentHeight(component);

		for (int j = y; j < height; j+=step) {
			for (int i = x; i < width; i+=step) {

				int rgb = bimg.getRGB(i, j);
				
				if (verifySinglePixel(i, j, rgb)) {

					//Clear Queue
					Queue<Point2D> queue = new LinkedList<Point2D>();
					Component found = new Component();

					Point2D firstPoint = new Point2D(i, j);

					//Mark as touched
					addPoint(found, firstPoint);
					addNeighbors(queue, firstPoint);

					//For each neighbor
					while (!queue.isEmpty()) {
						
						//Queue.pop(); 
						Point2D p = queue.remove();
												
						if (verifyNext(p, x, y, width, height, bimg)) {
							addPoint(found, p);
							addNeighbors(queue, p);
						} else {
							mask.setTouched(i, j);		
						}
					}

					if(this.validate(found)) {
						result.add(componentModifierStrategy.modifyComponent(found));
					}
				} else {
					mask.setTouched(i, j);
				}
			}
		}

		return result;
	}

	private boolean verifyNext(Point2D p, int x, int y, int width,
			int height, BufferedImage bimg) {
		
		int px = (int)p.getX();
		int py = (int)p.getY();
		
		int rgb = bimg.getRGB(px, py);

		if ((px >= x) && (px < width &&
				(py >= y) && (py < height))) {

			if (verifyPixel(px, py, rgb, bimg)) {
				return true;
			}
		}
		return false;
	}

	protected void addPoint(Component component, Point2D p) {
		mask.setTouched((int)p.getX(), (int)p.getY());
		component.add(p);
	}

	protected void addNeighbors(Queue<Point2D> queue, Point2D p) {
		addNeighbor(queue, (int)p.getX() + step, (int)p.getY(), p.getColor());
		addNeighbor(queue, (int)p.getX() - step, (int)p.getY(), p.getColor());
		addNeighbor(queue, (int)p.getX(), (int)p.getY() + step, p.getColor());
		addNeighbor(queue, (int)p.getX(), (int)p.getY() - step, p.getColor());
	}
	
	//It also prevents same pixel be included in a better list of neighbors
	//May have to be changed to let multiple touch
	protected void addNeighbor(Queue<Point2D> queue, int px, int py, int color) {
		if(!mask.isTouched(px, py)) {
			queue.add(new Point2D(px, py, color));	
		}
	}

	protected boolean verifyPixel(int px, int py, int rgb, BufferedImage bimg) {

		if (verifySinglePixel(px, py, rgb)) {
			if(!verifyNeighbors(px, py, bimg)) {
				return false;
			}
			return true;
		}

		return false;

	}

	private boolean verifySinglePixel(int px, int py, int rgb) {

		if(mask.isUnknown(px, py)) {
			if(pixelStrategy.validateColor(rgb)) {
				mask.setValid(px, py);
			} else {
				mask.setInvalid(px, py);
			}
		}

		return (!mask.isTouched(px, py) && mask.isValid(px, py));

	}

	private boolean verifyNeighbors(int px, int py, BufferedImage bimg) {

		int verified = 0;

		for(int j = py-step; j <= py+step; j += step) {
			for(int i = px-step; i <= px+step; i += step) {
				if(mask.isValid(i, j)) {
					verified++;
				} else if(pixelStrategy.validateColor(bimg.getRGB(i, j))) {
					verified++;
				}
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
