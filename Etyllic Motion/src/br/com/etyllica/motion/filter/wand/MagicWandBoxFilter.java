package br.com.etyllica.motion.filter.wand;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.Component;

public class MagicWandBoxFilter extends MagicWandConvexFilter {

	protected double distance = 0;
	
	protected int points = 0;
			
	public MagicWandBoxFilter(int w, int h) {
		super(w, h);
		
		border = 1;
	}

	public List<Component> filter(BufferedImage bimg, Component component){

		List<Component> result = new ArrayList<Component>();

		resetMask();

		int w = bimg.getWidth();
		int h = bimg.getHeight();

		int i = 0;
		int j = 0;

		Component currentComponent;

		for(j=border;j<h-border;j++){

			for(i=border;i<w-border;i++){

				if(validateColor(bimg.getRGB(i, j))){

					currentComponent = new Component(w, h);

					findPoints(i, j, bimg, currentComponent);

					if(validateComponent(currentComponent)){

						Component box = turnIntoBox(currentComponent);

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
						
						points = currentComponent.getPoints().size();
						
						if(a.distance(d)>a.distance(c)){
							distance = a.distance(d);
						}else{
							distance = a.distance(c);
						}
												
						result.add(box);				

					}

				}

			}

		}

		return result;
	}
	
	protected int findPoints(int i, int j, BufferedImage b, Component component){

		int w = b.getWidth();
		int h = b.getHeight();
		
		if(mask[i][j]){
			return 0;
		}else if (i==border||i==w-border||j==border||j==h-border){
			mask[i][j] = true;
			return 0;
		}


		int offsetX = 0;
		int offsetY = 0;

		//System.out.println("Found: "+i+" "+j);

		mask[i][j] = true;
		component.add(i, j);

		if(validateColor(b.getRGB(i+1, j))/*&&!validateColor(b.getRGB(i+1, j-1))*/){

			offsetX = 1;
			offsetY = 0;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}
		if(validateColor(b.getRGB(i+1, j+1))){

			offsetX = 1;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}

		if(validateColor(b.getRGB(i-1, j+1))){

			offsetX = -1;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}else if(validateColor(b.getRGB(i, j+1))){

			offsetX = 0;
			offsetY = 1;
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);

		}
		
		return component.getPoints().size();

	}

	protected void findOtherPoints(int i, int j, int offsetX, int offsetY, BufferedImage b, Component component){

		if(mask[i][j]){
			return;
		}else if (i==1||i==w-1||j==1||j==h-1){
			mask[i][j] = true;
			return;
		}
		
		if(validateColor(b.getRGB(i+offsetX, j+offsetY))){
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);	
		}else{
			findPoints(i, j, b, component);
		}

	}
	

	public int getPoints() {
		return points;
	}
	
	public double getDistance() {
		return distance;
	}

}
