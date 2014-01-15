package br.com.etyllica.motion.hollowcontroller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.ComponentFilter;
import br.com.etyllica.motion.core.Filter;
import br.com.etyllica.motion.features.Component;

public class FindRedLedActivatedFilter extends ComponentFilter implements Filter{

	public FindRedLedActivatedFilter(int w, int h) {
		super(w, h);
		
		border = 3;
		step = 2;
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		List<Component> result = new ArrayList<Component>();

		//for(Componente component: components){

			for (int j = component.getLowestY()+border; j < component.getHighestY()-border*2; j+=step) {

				for (int i = component.getLowestX()+border; i < component.getHighestX()-border*2; i+=step) {

					if (!validateColor(bimg.getRGB(i,j))) {
						return null;
					}					
				}
			}

		//}

		return result;

	}

	@Override
	public boolean validateColor(int rgb){

		boolean result = false;

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

		boolean rok = r>0xf0;
		boolean gok = g>0xf0;
		boolean bok = b>0xf0;

		if(rok&&gok&&bok){

			result = true;
		}


		return result;
	}

	@Override
	public boolean validateComponent(Component component){

		boolean result = false;

		if((component.getNumeroPontos()>=100)&&(component.getW()<w/6)){
			result = true;
		}

		return result;
	}


}
