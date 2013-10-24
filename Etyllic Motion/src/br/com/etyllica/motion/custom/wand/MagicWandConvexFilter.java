package br.com.etyllica.motion.custom.wand;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.ElasticFilter;

public class MagicWandConvexFilter extends ElasticFilter {

	protected double angle = 0;
	
	protected Color wandColor = Color.BLACK;
	
	protected int tolerance = 0x49;
	
	public MagicWandConvexFilter(int w, int h) {
		super(w, h);
	}

	public List<Component> filter(BufferedImage bimg, Component component){
				
		List<Component> result = new ArrayList<Component>();

		if(component.getPoints().size()<4){
			result.add(component);
						
			return result;
		}
		
		Component box = turnIntoBox(component);
		
		Ponto2D a = box.getPoints().get(0);
		Ponto2D b = box.getPoints().get(1);
		Ponto2D c = box.getPoints().get(2);
		Ponto2D d = box.getPoints().get(3);
		
		Ponto2D ac = new Ponto2D((a.getX()+c.getX())/2, (a.getY()+c.getY())/2);
		
		Ponto2D bd = new Ponto2D((b.getX()+d.getX())/2, (b.getY()+d.getY())/2);		
				
		Ponto2D rect = new Ponto2D(bd.getX(),ac.getY());		
		double dac = distance(bd, rect);
		double hip = distance(bd, ac);
		
		angle = Math.toDegrees(Math.asin(dac/hip));
		
		if(distance(a, c)>distance(a, b)){
			angle-=90;
		}
		
		result.add(box);
		
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

	@Override
	public boolean validateColor(int rgb) {
		//return isColor(rgb, wandColor.getRGB(), tolerance);
		return isSkin(rgb);
	}

	@Override
	public boolean validateComponent(Component component) {
		// TODO Auto-generated method stub
		return false;
	}

	protected double distance(Ponto2D a, Ponto2D b){
		
		double xdif = b.getX()-a.getX(); 
		double ydif = b.getY()-a.getY();
		
		double distance = Math.sqrt(xdif*xdif+ydif*ydif);
		
		return distance;
	}
	
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
