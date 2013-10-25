package br.com.etyllica.motion.custom.wand;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.features.Cross;
import br.com.etyllica.motion.filter.ElasticFilter;

public class FindCornersFilter extends ElasticFilter {

	private double angle = 0;
	
	private Cross cross = new Cross();
	
	public FindCornersFilter(int w, int h) {
		super(w, h);
	}

	public List<Component> filter(BufferedImage bimg, Component component){
		
		List<Component> result = new ArrayList<Component>();
			
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		
		Component holder = new Component(w,h);
		
		int border = 1;
		
		int i,j;

		for(j=border;j<h-border;j++){

			for(i=border;i<w-border;i++){

				if(validateColor(bimg.getRGB(i, j))){
					
					setCross(i,j,bimg);
										
					if(isCorner(cross)){
						holder.add(i, j);
					}

				}

			}

		}

		result.add(holder);
		
		return result;
	}
	
	private void setCross(int i, int j , BufferedImage b){
		
		cross.setUp(b.getRGB(i, j-1));
		cross.setDown(b.getRGB(i, j+1));
		cross.setLeft(b.getRGB(i-1, j));
		cross.setRight(b.getRGB(i+1, j));
		cross.setCenter(b.getRGB(i, j));
		
		cross.setLowerLeft(b.getRGB(i-1, j+1));
		cross.setUpperLeft(b.getRGB(i-1, j-1));
		
		cross.setLowerRight(b.getRGB(i+1, j+1));
		cross.setUpperRight(b.getRGB(i+1, j-1));		
		
	}
			
	private boolean isCorner(Cross cross){
				
		if(rightUpperCorner(cross)){
			return true;
		}else if(leftUpperCorner(cross)){
			return true;
		}else if(leftLowerCorner(cross)){
			return true;
		}else if(rightLowerCorner(cross)){
			return true;
		}else if(diagonalLeftCorner(cross)){
			return true;
		}else if(diagonalRightCorner(cross)){
			return true;
		}else if(diagonalUpCorner(cross)){
			return true;
		}else if(diagonalDownCorner(cross)){
			return true;
		}else if(leftValleyCorner(cross)){
			return true;
		}
		
		return false;
	}
		
	private boolean leftUpperCorner(Cross cross){
		boolean result = validateCross(cross, false, false, false, false, true, true, false, true, true)||
				validateCross(cross, false, false, false, false, true, true, false, true, false);
		
		return result;
	}

	private boolean rightUpperCorner(Cross cross){				
		boolean result = validateCross(cross, false, false, false, true, true, false, true, true, false)||
				validateCross(cross, false, false, false, true, true, false, false, true, false);
		
		return result;
	}
	
	private boolean leftLowerCorner(Cross cross){
		boolean result = validateCross(cross, false, true, true, false, true, true, false, false, false)||
				validateCross(cross, false, true, false, false, true, true, false, false, false);
		
		return result;
	}

	private boolean rightLowerCorner(Cross cross){		
		boolean result = validateCross(cross, true, true, false, true, true, false, false, false, false)||
				validateCross(cross, false, true, false, true, true, false, false, false, false);
		return result;
	}
	
	private boolean diagonalUpCorner(Cross cross){
		return validateCross(cross, false, false, false, false, true, false, true, true, true);
	}
	
	private boolean diagonalLeftCorner(Cross cross){
		return validateCross(cross, false, false, true, false, true, true, false, false, true);
	}
	
	private boolean diagonalRightCorner(Cross cross){
		return validateCross(cross, true, false, false, true, true, false, true, false, false);
	}
	
	private boolean diagonalDownCorner(Cross cross){
		return validateCross(cross, true, true, true, false, true, false, false, false, false);
	}
	
	private boolean leftValleyCorner(Cross cross){
		return validateCross(cross, true, true, true, true, false, false, true, true, false);
	}
	
	private boolean validateCross(Cross cross, boolean upperLeft, boolean up, boolean upperRight, boolean left, boolean center, boolean right, boolean lowerLeft, boolean down, boolean lowerRight){
		
		boolean result = validateColor(cross.getUpperLeft())==upperLeft&&
						 validateColor(cross.getUp())==up&&
						 validateColor(cross.getUpperRight())==upperRight&&
				         validateColor(cross.getLeft())==left&&
				         validateColor(cross.getCenter())==center&&
				         validateColor(cross.getRight())==right&&
				         validateColor(cross.getLowerLeft())==lowerLeft&&
				         validateColor(cross.getDown())==down&&
				         validateColor(cross.getLowerRight())==lowerRight;				         

		return result;
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

	public double getAngle() {
		return angle;
	}
		
}
