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
	
	public FloodFillSearch(int w, int h) {
		super(w, h, new SkinColorStrategy());
		
		this.validations.add(new CountComponentPoints(180));
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

				if (!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i,j))) {
										
					Queue<Point2D> queue = new LinkedList<Point2D>();
					queue.add(new Point2D(i, j));

					Component lista = new Component();

					while (!queue.isEmpty()) {
						
						Point2D p = queue.remove();

						if ((p.getX() >= x) && (p.getX() < width &&
								(p.getY() >= y) && (p.getY() < height))) {
							
							if (!mask[(int)p.getX()][(int)p.getY()] && pixelStrategy.validateColor(bimg.getRGB((int)p.getX(), (int)p.getY()))) {
								mask[(int)p.getX()][(int)p.getY()] = true;

								lista.add(p);

								queue.add(new Point2D((int)p.getX() + step, (int)p.getY()));
								queue.add(new Point2D((int)p.getX() - step, (int)p.getY()));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() + step));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() - step));

								//queue.add(new Ponto((int)p.getX() + 1, (int)p.getY() + 1));
							}
							
						}
						
					}

					if(this.validate(lista)) {

						result.add(componentModifierStrategy.modifyComponent(lista));

					}

				} else {
					
					mask[i][j] = true;
					
				}
				
			}
		}

		return result;
	}

}
