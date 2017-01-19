package br.com.etyllica.motion.filter.search;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.Cross;

public class CornerSearch extends BooleanMaskSearch {

	private Cross cross = new Cross();

	public CornerSearch(int w, int h) {
		super(w, h);
	}
	
	public Component filterFirst(ImageSource bimg, Component component){
		super.setup(component.getW(), component.getH());
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		lastComponent.reset();
		
		for(int j=border;j<h-border*2;j+=step){

			for(int i=border;i<w-border*2;i+=step){

				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j))){

					setCross(i,j,bimg);

					if(isCorner(cross)){
						lastComponent.add(i, j);
						mask[i][j] = true;
					}

				}

			}

		}

		return lastComponent;
	}

	public List<Component> filter(ImageSource bimg, Component component){
		super.setup(component.getW(), component.getH());
		
		List<Component> result = new ArrayList<Component>();

		int w = bimg.getWidth();
		int h = bimg.getHeight();

		Component holder = new Component(w,h);

		for(int j=border;j<h-border*2;j+=step){

			for(int i=border;i<w-border*2;i+=step){

				if(pixelStrategy.validateColor(bimg.getRGB(i, j))){

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

	private void setCross(int i, int j , ImageSource b){

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
		boolean result = validateCross(cross, "FFF FTT FT(T|F)");

		return result;
	}

	private boolean rightUpperCorner(Cross cross){
		boolean result = validateCross(cross, "FFF TTF (T|F)TF");

		return result;
	}

	private boolean leftLowerCorner(Cross cross){

		boolean result = validateCross(cross, "FT(T|F) FTT FFF");

		return result;
	}

	private boolean rightLowerCorner(Cross cross){
		boolean result = validateCross(cross, "(T|F)TF TTF FFF");
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
		return validateCross(cross, "FFF FT(T|F) (T|F)T(T|F)");
	}

	private boolean downHardCorner(Cross cross){
		return validateCross(cross, "(T|F)T(T|F) (T|F)TF FFF");
	}

	private boolean leftHardCorner(Cross cross){
		return validateCross(cross, "F(T|F)(T|F) F(T|F)T FF(T|F)");
	}

	private boolean rightHardCorner(Cross cross){
		return validateCross(cross, "(T|F)(T|F)F T(T|F)F (T|F)FF");
	}

	public boolean validateCross(Cross cross, String pattern){

		boolean result = getCrossString(cross).matches(pattern.replaceAll(" ", ""));

		return result;

	}

	public boolean validateCross(Cross cross, boolean upperLeft, boolean up, boolean upperRight, boolean left, boolean center, boolean right, boolean lowerLeft, boolean down, boolean lowerRight){

		boolean result = pixelStrategy.validateColor(cross.getUpperLeft())==upperLeft&&
				pixelStrategy.validateColor(cross.getUp())==up&&
				pixelStrategy.validateColor(cross.getUpperRight())==upperRight&&
				pixelStrategy.validateColor(cross.getLeft())==left&&
				pixelStrategy.validateColor(cross.getCenter())==center&&
				pixelStrategy.validateColor(cross.getRight())==right&&
				pixelStrategy.validateColor(cross.getLowerLeft())==lowerLeft&&
				pixelStrategy.validateColor(cross.getDown())==down&&
				pixelStrategy.validateColor(cross.getLowerRight())==lowerRight;

		return result;
	}	

	public String getCrossString(Cross cross){

		StringBuilder builder = new StringBuilder();

		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getUpperLeft())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getUp())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getUpperRight())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getLeft())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getCenter())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getRight())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getLowerLeft())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getDown())));
		builder.append(booleanToChar(pixelStrategy.validateColor(cross.getLowerRight())));

		return builder.toString();
	}

	private char booleanToChar(boolean b){

		if(b){
			return 'T';
		}

		return 'F';

	}

}
