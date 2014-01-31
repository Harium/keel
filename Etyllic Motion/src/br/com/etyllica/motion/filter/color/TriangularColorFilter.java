package br.com.etyllica.motion.filter.color;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;

public class TriangularColorFilter extends ColorFilter{

	public TriangularColorFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component){
		
		super.setup();
				
		int x = component.getLowestX()+border;
		int y = component.getLowestY()+border;
		
		int w = component.getW()-border;
		int h = component.getH()-border;
		
		for(int j=y;j<h;j++){
			
			for(int i=x;i<w;i++){
				
				if(!mask[i][j]&&validateColor(bimg.getRGB(i, j))){
					
					int width = findHorizontalLimit(bimg, i, j, w);
					
					int height = findVerticalLimit(bimg, i, j, h);
									
					lastComponent.setBounds(i, j, width, height);
										
					return lastComponent;

				}

			}

		}

		return lastComponent;
		
	}
	
	@Override
	public List<Component> filter(BufferedImage bimg, Component component){
		
		super.setup();
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();

		int i,j;

		for(j=border;j<h-border;j++){

			for(i=border;i<w-border;i++){

				if(!mask[i][j]&&validateColor(bimg.getRGB(i, j))){
					
					int width = findHorizontalLimit(bimg, i, j, w);
					
					int height = findVerticalLimit(bimg, i, j, h);
									
					lastComponent.setBounds(i, j, width, height);
					
					Component holder = new BoundingComponent(i, j, i+width, j+height);
										
					updateMask(i, j, width, height, true);
					
					result.add(holder);
										
				}

			}

		}
		
		return result;
	}
	
	private int findHorizontalLimit(BufferedImage bimg, int i, int j, int w){
		
		int totalWidth = 0;
		
		for(int ni=i;ni<w;ni++){
			
			if(!mask[ni][j]&&validateColor(bimg.getRGB(ni, j))){
				totalWidth++;
			}else{
				break;
			}
			
		}
		
		return totalWidth;
		
	}
	
	private int findVerticalLimit(BufferedImage bimg, int i, int j, int h){
		
		int totalHeight = 0;
		
		for(int nj=j;nj<h;nj++){
			
			if(!mask[i][nj]&&validateColor(bimg.getRGB(i, nj))){
				totalHeight++;
			}else{
				break;
			}
			
		}
		
		return totalHeight;
		
	}
		
}
