package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.dynamic.DynamicMaskSearch;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;

public class FloodFillSearch extends DynamicMaskSearch {

	private int minNeighbors = 1;

	private int maxNeighbors = 9;
	
	private Component lastComponent;

	public FloodFillSearch(int w, int h) {
		super(w, h, new SkinColorStrategy());
	}

	public FloodFillSearch(int w, int h, int minNeighbors) {
		super(w, h, new SkinColorStrategy());

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

				if (!isTouched(i, j) && verifySinglePixel(i, j, bimg)) {

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

								setTouched(px, py);

								found.add(p);

								queue.add(new Point2D(px + step, py));
								queue.add(new Point2D(px - step, py));
								queue.add(new Point2D(px, py + step));
								queue.add(new Point2D(px, py - step));

							}

						}

					}

					if(this.validate(found)) {

						result.add(componentModifierStrategy.modifyComponent(found));

					}

				}

				setTouched(i, j);

			}
		}

		return result;
	}

	private boolean verifyPixel(int px, int py, BufferedImage bimg) {

		if (verifySinglePixel(px, py, bimg)) {

			if(verifyNeighbors(px, py, bimg)) {

				return true;
			}			
		}

		return false;

	}

	private boolean verifySinglePixel(int px, int py, BufferedImage bimg) {

		if(isUnknown(px, py)) {

			if(pixelStrategy.validateColor(bimg.getRGB(px, py))) {

				setValid(px, py);

			} else {

				setInvalid(px, py);
			}
		}

		return (!isTouched(px, py) && isValid(px, py));

	}

	private boolean verifyNeighbors(int px, int py, BufferedImage bimg) {

		int verified = 0;

		for(int j = py-step; j <= py+step; j += step) {

			for(int i = px-step; i <= px+step; i += step) {

				if(pixelStrategy.validateColor(bimg.getRGB(i, j))) {
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
