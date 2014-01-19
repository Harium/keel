package br.com.etyllica.motion.filter.color;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.FilterImpl;
import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;

public class ColorFilter extends FilterImpl {

	protected int border = 1;
	
	protected int tolerance = 0x40;

	protected int color = Color.BLACK.getRGB();
	
	protected Component lastComponent = new BoundingComponent(0, 0, 1, 1);

	public ColorFilter() {
		super();
	}
	
	@Override
	public Component filterFirst(BufferedImage bimg, Component component){
		
		super.setup();
		
		int x = component.getLowestX();
		int y = component.getLowestY();
		
		int w = component.getW();
		int h = component.getH();
		
		for(int j=y+border;j<h-border;j++){
			
			for(int i=x+border;i<w-border;i++){
				
				if(validateColor(bimg.getRGB(i, j))){
					
					lastComponent.setLocation(i, j);
										
					return lastComponent;

				}

			}

		}

		return lastComponent;
		
	}
	
	public List<Component> filter(BufferedImage bimg, Component component){
		
		super.setup();
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		Component holder = new Component(w,h);

		int i,j;

		for(j=border;j<h-border;j++){

			for(i=border;i<w-border;i++){

				if(validateColor(bimg.getRGB(i, j))){

					holder.add(i, j);
					
				}

			}

		}
		
		result.add(holder);

		return result;
	}
	
	public List<Component> filter(BufferedImage bimg, List<Component> components){
		return null;
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, this.color, tolerance);
	}

	@Override
	public boolean validateComponent(Component component) {
		// TODO Auto-generated method stub
		return true;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;	
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}
	
	public Component getLastComponent() {
		return lastComponent;
	}
	
}
