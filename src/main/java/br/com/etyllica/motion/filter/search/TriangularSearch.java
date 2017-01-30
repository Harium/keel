package br.com.etyllica.motion.filter.search;

import java.util.List;

import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;

public class TriangularSearch extends BooleanMaskSearch{

	public TriangularSearch(int w, int h) {
		super(w, h);
	}

	@Override
	public Component filterFirst(ImageSource bimg, Component component){
		super.setup(component.getW(), component.getH());
		
		Component lastComponent = new Component(0, 0, w, h);
								
		int x = component.getLowestX();
		int y = component.getLowestY();
		
		int w = component.getW()-border*2;
		int h = component.getH()-border*2;
		
		//TODO Swap i,j
		for(int j=y;j<h;j+=step){
			
			for(int i=x;i<w;i+=step){
				
				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)){
					
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
	public List<Component> filter(ImageSource bimg, Component component){
		super.setup(component.getW(), component.getH());
		
		int x = border;
		int y = border;
		
		int w = bimg.getWidth()-border*2;
		int h = bimg.getHeight()-border*2;

		//TODO Swap i,j
		for(int j=y;j<h;j+=step){

			for(int i=x;i<w;i+=step){

				if(!mask[i][j]&&pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)){
					
					int width = findHorizontalLimit(bimg, i, j, w);
					
					int height = findVerticalLimit(bimg, i, j, h);
					
					Component holder = new Component(i, j, i+width, j+height);
										
					updateMask(i, j, width, height, true);
					
					result.add(holder);

				}

			}

		}
		
		return result;
	}
	
	private int findHorizontalLimit(ImageSource bimg, int i, int j, int w){
		
		int totalWidth = 0;
		
		for(int ni=i;ni<w;ni++){
			
			if(!mask[ni][j]&&pixelStrategy.validateColor(bimg.getRGB(ni, j), ni, j)){
				totalWidth++;
			}else{
				break;
			}
			
		}
		
		return totalWidth;
		
	}
	
	private int findVerticalLimit(ImageSource bimg, int i, int j, int h){
		
		int totalHeight = 0;
				
		for(int nj=j;nj<h;nj++){
			
			if(!mask[i][nj]&&pixelStrategy.validateColor(bimg.getRGB(i, nj), i, nj)){
				totalHeight++;
			}else{
				break;
			}
			
		}
		
		return totalHeight;
		
	}
		
}
