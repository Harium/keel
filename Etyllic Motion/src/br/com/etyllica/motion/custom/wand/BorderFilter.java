package br.com.etyllica.motion.custom.wand;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.features.Cross;
import br.com.etyllica.motion.filter.ElasticFilter;

public class BorderFilter extends ElasticFilter {

	private int step = 1;
	
	protected int border = 1;
	
	private int color = Color.BLACK.getRGB();
	
	private Cross cross = new Cross();
	
	public BorderFilter(int w, int h) {
		super(w, h);
	}

	public List<Component> filter(BufferedImage bimg, Component component){
		
		List<Component> result = new ArrayList<Component>();
			
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		
		Component holder = new Component(w,h);
				
		int i,j;
		
		border = step;

		for(j=border;j<h-border;j+=step){

			for(i=border;i<w-border;i+=step){

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
		
		cross.setUp(b.getRGB(i, j-step));
		cross.setDown(b.getRGB(i, j+step));
		cross.setLeft(b.getRGB(i-step, j));
		cross.setRight(b.getRGB(i+step, j));
		cross.setCenter(b.getRGB(i, j));
		
		cross.setLowerLeft(b.getRGB(i-step, j+step));
		cross.setUpperLeft(b.getRGB(i-step, j-step));
		
		cross.setLowerRight(b.getRGB(i+step, j+step));
		cross.setUpperRight(b.getRGB(i+step, j-step));
		
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
		}
		
		//Hard Alias 
		else if(upHardCorner(cross)){
			return true;
		}else if(downHardCorner(cross)){
			return true;
		}else if(leftHardCorner(cross)){
			return true;
		}else if(rightHardCorner(cross)){
			return true;
		}
		
		return false;
	}
		
	private boolean leftUpperCorner(Cross cross){
		boolean result = validateCross(cross, "FFF FTT FT.");
		
		return result;
	}

	private boolean rightUpperCorner(Cross cross){				
		boolean result = validateCross(cross, "FFF TTF .TF");
		
		return result;
	}
	
	private boolean leftLowerCorner(Cross cross){
				
		boolean result = validateCross(cross, "FT. FTT FFF");
		
		return result;
	}

	private boolean rightLowerCorner(Cross cross){
		boolean result = validateCross(cross, ".TF TTF FFF");
		return result;
	}
	
	private boolean diagonalUpCorner(Cross cross){
		return validateCross(cross, "FFF FTF TTT");
	}
	
	private boolean diagonalLeftCorner(Cross cross){
		return validateCross(cross, "FFT FTT FFT");
	}
	
	private boolean diagonalRightCorner(Cross cross){
		return validateCross(cross, "TFF TTF TFF");
	}
	
	private boolean diagonalDownCorner(Cross cross){
		return validateCross(cross, "TTT FTF FFF");
	}
		
	private boolean upHardCorner(Cross cross){
		return validateCross(cross, "FFF FTT TTT");
	}
	
	private boolean downHardCorner(Cross cross){
		return validateCross(cross, "TTT TTF FFF");
	}
	
	private boolean leftHardCorner(Cross cross){
		return validateCross(cross, "FFT FTT FFF");
	}
		
	private boolean rightHardCorner(Cross cross){
		return validateCross(cross, "TFF TTF TTF");
	}
	
	public boolean validateCross(Cross cross, String pattern){
		
		boolean result = getCrossString(cross).matches(pattern.replaceAll(" ", ""));

		return result;
		
	}
	
	public boolean validateCross(Cross cross, boolean upperLeft, boolean up, boolean upperRight, boolean left, boolean center, boolean right, boolean lowerLeft, boolean down, boolean lowerRight){
		
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
		return isColor(rgb, this.color, 0x40);
	}
	
	@Override
	public boolean validateComponent(Component component) {
		// TODO Auto-generated method stub
		return true;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color.getRGB();
	}
	
	public String getCrossString(Cross cross){

		StringBuilder builder = new StringBuilder();
		
		builder.append(booleanToChar(validateColor(cross.getUpperLeft())));
		builder.append(booleanToChar(validateColor(cross.getUp())));
		builder.append(booleanToChar(validateColor(cross.getUpperRight())));
		builder.append(booleanToChar(validateColor(cross.getLeft())));
		builder.append(booleanToChar(validateColor(cross.getCenter())));
		builder.append(booleanToChar(validateColor(cross.getRight())));
		builder.append(booleanToChar(validateColor(cross.getLowerLeft())));
		builder.append(booleanToChar(validateColor(cross.getDown())));
		builder.append(booleanToChar(validateColor(cross.getLowerRight())));
		
		return builder.toString();
	}
	
	private char booleanToChar(boolean b){
		
		if(b){
			return 'T';
		}
		
		return 'F';
			
	}
		
}
