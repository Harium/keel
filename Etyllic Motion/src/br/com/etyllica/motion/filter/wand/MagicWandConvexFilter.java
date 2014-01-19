package br.com.etyllica.motion.filter.wand;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ElasticFilter;
import br.com.etyllica.motion.features.Component;

public class MagicWandConvexFilter extends ElasticFilter {

	protected double angle = 0;
		
	protected Color wandColor = Color.BLACK;
	
	protected int tolerance = 0x49;
		
	public MagicWandConvexFilter(int w, int h) {
		super(w, h);
	}

	public List<Component> filter(BufferedImage bimg, Component component){
				
		super.setup();

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
		
		result.add(box);
		
		return result;
	}
	
	protected Component turnIntoBox(Component component){

		//System.out.println("Degenerating "+component.getPoints().size()+" points into 4.");

		Point2D a = component.getPoints().get(0); //Lower X
		Point2D b = component.getPoints().get(0); //Lower equal Y
		Point2D c = component.getPoints().get(0); //Higher Y
		Point2D d = component.getPoints().get(0); //Higher equal X

		for(Point2D point: component.getPoints()){

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

	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, wandColor.getRGB(), tolerance);
		//return isSkin(rgb);
	}

	@Override
	public boolean validateComponent(Component component) {
		
		return component.getPoints().size()>30;
	}

	/*protected double distance(Ponto2D a, Ponto2D b){
		
		double xdif = b.getX()-a.getX(); 
		double ydif = b.getY()-a.getY();
		
		double distance = Math.sqrt(xdif*xdif+ydif*ydif);
		
		return distance;
	}*/
	
	public double getAngle() {
		return angle;
	}
	
	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}

	public Color getWandColor() {
		return wandColor;
	}

	public void setWandColor(Color wandColor) {
		this.wandColor = wandColor;
	}
	
}
