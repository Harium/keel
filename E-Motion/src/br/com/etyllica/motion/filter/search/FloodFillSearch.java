package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;
import br.com.etyllica.motion.filter.validation.CountComponentPoints;

public class FloodFillSearch extends BooleanMaskSearch {

	private Component lastComponent;

	private int minNeighbors = 1;

	public FloodFillSearch(int w, int h) {
		super(w, h, new SkinColorStrategy());

		this.validations.add(new CountComponentPoints(180));
	}
	
	public FloodFillSearch(int w, int h, int minNeighbors) {
		super(w, h, new SkinColorStrategy());
		
		this.minNeighbors = minNeighbors;
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {

		List<Component> list = filter(bimg, component);

		if(!list.isEmpty()){

			lastComponent = list.get(0);

		}

		return lastComponent;

	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {

		super.setup();

		int x = border;

		int y = border;

		int width = component.getW()-border*2;

		int height = component.getH()-border*2;

		for (int j = y; j < height; j+=step) {

			for (int i = x; i < width; i+=step) {

				if (!mask[i][j] && pixelStrategy.validateColor(bimg.getRGB(i,j))) {

					Queue<Point2D> queue = new LinkedList<Point2D>();

					queue.add(new Point2D(i, j));

					Component found = new Component();

					while (!queue.isEmpty()) {

						Point2D p = queue.remove();

						int px = (int)p.getX();
						int py = (int)p.getY();

						if ((px >= x) && (px < width &&
								(py >= y) && (py < height))) {

							if (verifyPixel(px, py, bimg)) {

								mask[(int)p.getX()][(int)p.getY()] = true;

								found.add(p);

								queue.add(new Point2D((int)p.getX() + step, (int)p.getY()));
								queue.add(new Point2D((int)p.getX() - step, (int)p.getY()));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() + step));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() - step));

							}

						}

					}

					if(this.validate(found)) {

						result.add(componentModifierStrategy.modifyComponent(found));

					}

				} else {

					mask[i][j] = true;

				}

			}
		}

		return result;
	}
	
	private boolean verifyPixel(int px, int py, BufferedImage bimg) {

		if (verifySinglePixel(px, py, bimg)) {

			return (minNeighbors<=1)||verifyNeighbors(px, py, bimg);

		}

		return false;

	}

	private boolean verifySinglePixel(int px, int py, BufferedImage bimg) {

		return (!mask[px][py] && pixelStrategy.validateColor(bimg.getRGB(px, py)));

	}

	private boolean verifyNeighbors(int px, int py, BufferedImage bimg) {
		
		int verified = 0;
		
		for(int j=py-1; j<py+1; j++) {
				
			for(int i=px-1; i<px+1; i++) {
				
				if(pixelStrategy.validateColor(bimg.getRGB(i, j))) {
					verified++;
				}
				
			}
		
		}		
				
		return verified >= minNeighbors;
	}

	public int getMinNeighbors() {
		return minNeighbors;
	}

	public void setMinNeighbors(int minNeighbors) {
		this.minNeighbors = minNeighbors;
	}
	
}
