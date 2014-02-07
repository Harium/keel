package br.com.etyllica.motion.filter.modifier;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;

public class AugmentedMarkerModifier implements ComponentModifierStrategy {

	protected double distance = 0;

	protected int points = 0;

	protected double angle = 0;

	public AugmentedMarkerModifier() {
		super();
	}

	public Component modifyComponent(Component component){

		Component box = turnIntoBox(component);

		Point2D a = box.getPoints().get(0);
		Point2D b = box.getPoints().get(1);
		Point2D c = box.getPoints().get(2);
		Point2D d = box.getPoints().get(3);

		Point2D ac = new Point2D((a.getX()+c.getX())/2, (a.getY()+c.getY())/2);

		Point2D bd = new Point2D((b.getX()+d.getX())/2, (b.getY()+d.getY())/2);

		Point2D rect = new Point2D(bd.getX(),ac.getY());		
		double dac = bd.distance(rect);
		double hip = bd.distance(ac);

		angle = Math.toDegrees(Math.asin(dac/hip));

		if(a.distance(c)>a.distance(b)){
			angle-=90;
		}

		points = component.getPoints().size();

		if(a.distance(d)>a.distance(c)){
			distance = a.distance(d);
		}else{
			distance = a.distance(c);
		}

		return box;
	}

	protected Component turnIntoBox(Component component){

		//System.out.println("Degenerating "+component.getPoints().size()+" points into 4.");

		Point2D a = component.getPoints().get(0); //Lower X
		Point2D b = component.getPoints().get(0); //Lower equal Y
		Point2D c = component.getPoints().get(0); //Higher Y
		Point2D d = component.getPoints().get(0); //Higher equal X

		for(Point2D point: component.getPoints()){

			if(point.getX()<a.getX()&&(point.getX()*point.getX())<a.getX()*a.getY()){
				a = point;
			}else if(point.getY()>=c.getY()){

				if(point.getY()>c.getY()||point.getX()<c.getX()){
					c = point;
				}

			}

			if(point.getY()<=b.getY()){
				b = point;
			}else if(point.getX()>=d.getX()){
				d = point;
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

	public double getDistance() {
		return distance;
	}

	public double getAngle() {
		return angle;
	}

}
