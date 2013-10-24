package br.com.etyllica.motion.custom.wand;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Component;

public class MagicWandBoxFilter extends MagicWandConvexFilter {

	
	
	public MagicWandBoxFilter(int w, int h) {
		super(w, h);
	}

	public List<Component> filter(BufferedImage bimg, Component component){
		
		List<Component> result = new ArrayList<Component>();
	
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		//Find First Point
		boolean found = false;

		int i = 0;
		int j = 0;

		for(j=0;j<h;j++){

			for(i=0;i<w;i++){

				if(validateColor(bimg.getRGB(i, j))){

					found = true;
					break;

				}

			}

			if(found){
				break;
			}

		}

		//Points are to Down, Left or Right
		findPoints(i, j, bimg, component);

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

	private void findPoints(int i, int j, BufferedImage b, Component component){

		int w = b.getWidth();
		int h = b.getHeight();

		int offsetX = 0;
		int offsetY = 0;

		System.out.println("Found: "+i+" "+j);
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

		System.out.println("Return: "+i+" "+j);

	}
	
	private void findOtherPoints(int i, int j, int offsetX, int offsetY, BufferedImage b, Component component){

		if(validateColor(b.getRGB(i+offsetX, j+offsetY))){
			findOtherPoints(i+offsetX, j+offsetY, offsetX, offsetY, b, component);	
		}else{
			findPoints(i, j, b, component);
		}

	}

	@Override
	public boolean validateColor(int rgb) {
		return isBlack(rgb);
	}

	@Override
	public boolean validateComponent(Component component) {
		// TODO Auto-generated method stub
		return false;
	}
		
}
