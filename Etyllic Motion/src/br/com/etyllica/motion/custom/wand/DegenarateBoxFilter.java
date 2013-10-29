package br.com.etyllica.motion.custom.wand;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.FilterImpl;

public class DegenarateBoxFilter extends FilterImpl {

	protected double distance = 0;

	protected int points = 0;
	
	protected double angle = 0;

	public DegenarateBoxFilter() {
		super();
	}

	public List<Component> filter(BufferedImage bimg, List<Component> components){

		List<Component> result = new ArrayList<Component>();

		for(Component component: components){

			if(validateComponent(component)){

				Component box = turnIntoBox(component);

				Ponto2D a = box.getPoints().get(0);
				Ponto2D b = box.getPoints().get(1);
				Ponto2D c = box.getPoints().get(2);
				Ponto2D d = box.getPoints().get(3);

				Ponto2D ac = new Ponto2D((a.getX()+c.getX())/2, (a.getY()+c.getY())/2);

				Ponto2D bd = new Ponto2D((b.getX()+d.getX())/2, (b.getY()+d.getY())/2);

				Ponto2D rect = new Ponto2D(bd.getX(),ac.getY());		
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

				result.add(box);	

			}
		}

		return result;
	}
	
	protected Component turnIntoBox(Component component){

		//System.out.println("Degenerating "+component.getPoints().size()+" points into 4.");

		Ponto2D a = component.getPoints().get(0); //Lower X
		Ponto2D b = component.getPoints().get(0); //Lower equal Y
		Ponto2D c = component.getPoints().get(0); //Higher Y
		Ponto2D d = component.getPoints().get(0); //Higher equal X

		for(Ponto2D point: component.getPoints()){

			if(point.getX()<a.getX()){
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

	@Override
	public boolean validateColor(int rgb) {
		return true;
	}

	@Override
	public boolean validateComponent(Component component) {
		return component.getPoints().size()>=4;
	}

}
