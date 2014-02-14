package br.com.etyllica.motion.filter.modifier;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;

public class AugmentedMarkerModifier implements ComponentModifierStrategy {

	protected int points = 0;

	protected double angleX = 0;

	protected double angleY = 0;

	public AugmentedMarkerModifier() {
		super();
	}

	public Component modifyComponent(Component component){

		Component box = envelope(component);

		Point2D a = box.getPoints().get(0);
		Point2D b = box.getPoints().get(1);
		Point2D c = box.getPoints().get(2);
		Point2D d = box.getPoints().get(3);

		double lowerDistance = c.distance(d);

		double higherDistance = a.distance(b);

		double lateralDistance = a.distance(c)/b.distance(d);

		double factor = lowerDistance/higherDistance*lateralDistance;

		Point2D ab = new Point2D((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
		Point2D cd = new Point2D((c.getX()+d.getX())/2, (c.getY()+d.getY())/2);

		angleY = (ab.angle(cd)-90)/1.4;

		angleX = (factor-1)*90/0.5;
		angleX += (angleY*angleY)/50;

		//negative
		if(factor<1){
			angleX *= -1; 
		}

		points = component.getPoints().size();

		return box;
	}

	protected Component envelope(Component component){

		Point2D center = component.getCenter();

		Point2D a = center; //Lower X
		Point2D b = center; //Lower equal Y
		Point2D c = center; //Higher Y
		Point2D d = center; //Higher equal X
				
		for(Point2D point: component.getPoints()){
			
			//Verify A region
			if(point.getY()<a.getY()){
				a = point;
				continue;
			}
			
			if(point.distance(a)>d.distance(a)&&point.getY()>=center.getY()){
				d = point;
				continue;
			}

			if(point.getX()<=center.getX()){

				//if(point.getY()>center.getY()){
					
					if(point.getX()<=c.getX()){
						c = point;
						continue;
					}
					
				//}

			}else{

				if(point.getY()<=center.getY()&&point.distance(center)>b.distance(center)){
						b = point;
						continue;
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

	public double getAngleX() {
		return angleX;
	}

	public double getAngleY() {
		return angleY;
	}

}
