package br.com.etyllica.keel.filter.search.flood;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;


public class ExpandableFloodFillSearch extends FloodFillSearch {
	
	public ExpandableFloodFillSearch(int w, int h) {
		super(w, h);
	}

	public ExpandableFloodFillSearch(int w, int h, int minNeighbors) {
		super(w, h, minNeighbors);
	}

	public ExpandableFloodFillSearch(int w, int h, int minNeighbors, int maxNeighbors) {
		super(w, h, minNeighbors, maxNeighbors);
	}

	@Override
	public boolean inBoundary(int px, int py) {
		return px > step || px <= getW()-step || py > step || py <= getH()-step;
	}
	
	@Override
	public List<Component> filter(final ImageSource bimg, final Component component) {
		super.setup(component.getW(), component.getH());
		
		boundary = component;

		int x = component.getX()+border;
		int y = component.getY()+border;

		int width = getComponentWidth(component);
		int height = getComponentHeight(component);

		//TODO Swap i,j
		for (int j = y; j < y+height; j+=step) {
			for (int i = x; i < x+width; i+=step) {
				if(!component.isInside(i, j)) {
					continue;
				}

				int rgb = bimg.getRGB(i, j);
				
				if (verifySinglePixel(i, j, rgb)) {

					//Clear Queue
					Queue<Point2D> queue = new LinkedList<Point2D>();
					Component found = new Component();

					Point2D firstPoint = new Point2D(i, j, rgb);

					//Mark as touched
					addPoint(found, firstPoint);
					addNeighbors(queue, firstPoint);

					//For each neighbor
					while (!queue.isEmpty()) {
						
						//Queue.pop(); 
						Point2D p = queue.remove();
												
						if (verifyNext(p, i, j, getW(), getH(), bimg)) {
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
	
}
