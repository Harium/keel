package br.com.etyllica.motion.modifier;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;

public class AugmentedMarkerModifier implements ComponentModifierStrategy, ComponentModifier<Component> {

	protected int points = 0;

	public AugmentedMarkerModifier() {
		super();
	}

	public Component modifyComponent(Component component) {

		Component box = modify(component);

		//Point2D a = box.getPoints().get(0);
		//Point2D b = box.getPoints().get(1);
		//Point2D c = box.getPoints().get(2);
		//Point2D d = box.getPoints().get(3);

		points = component.getPoints().size();

		return box;
	}

	public Component modify(Component component) {

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

				if(point.getX()<=c.getX()) {
					c = point;
				}

			}

		}

		Component box = new Component(component.getW(), component.getH());

		box.add(a);
		box.add(b);
		box.add(c);
		box.add(d);

		return box;

	}

	public int getPoints() {
		return points;
	}

}
