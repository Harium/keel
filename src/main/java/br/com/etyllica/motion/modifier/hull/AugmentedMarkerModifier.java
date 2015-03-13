package br.com.etyllica.motion.modifier.hull;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;

public class AugmentedMarkerModifier implements HullModifier {

	protected int points = 0;

	public AugmentedMarkerModifier() {
		super();
	}

	public Component modifyComponent(Component component) {

		Component box = new Component();

		for(Point2D point: modify(component)) {
			box.add(point);
		}

		//Point2D a = box.getPoints().get(0);
		//Point2D b = box.getPoints().get(1);
		//Point2D c = box.getPoints().get(2);
		//Point2D d = box.getPoints().get(3);

		points = component.getPoints().size();

		return box;
	}

	public List<Point2D> modify(Component component) {

		Point2D center = component.getCenter();

		Point2D a = center; //Lower X
		Point2D b = center; //Lower equal Y
		Point2D c = center; //Higher Y
		Point2D d = center; //Higher equal X

		for(Point2D point: component.getPoints()) {

			//Verify A region
			if(point.getY()<a.getY()) {

				a = point;
				continue;

			} else if(point.distance(a)>d.distance(a) || (point.distance(b)+point.distance(c)>d.distance(b)+d.distance(c))) {

				d = point;
				continue;

			}

			if(point.getX()>center.getX()) {

				if(point.getX()>=b.getX()&&point.distance(c)>b.distance(c)) {
					b = point;
				}

			}else{

				if(point.getX() <= c.getX()) {

					c = point;
				}

			}

		}
		
		List<Point2D> list = new ArrayList<Point2D>();
				
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		
		return list;

	}
	
	public int getPoints() {
		return points;
	}

}
