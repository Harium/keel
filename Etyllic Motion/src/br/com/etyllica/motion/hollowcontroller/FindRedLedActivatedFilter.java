package br.com.etyllica.motion.hollowcontroller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Componente;
import br.com.etyllica.motion.filter.ComponentFilter;
import br.com.etyllica.motion.filter.Filter;

public class FindRedLedActivatedFilter extends ComponentFilter implements Filter{

	public FindRedLedActivatedFilter(int w, int h) {
		super(w, h);
		
		border = 3;
		step = 2;
	}

	@Override
	public List<Componente> filter(BufferedImage bimg, Componente component) {
		
		List<Componente> result = new ArrayList<Componente>();

		//for(Componente component: components){

			for (int j = component.getMenorY()+border; j < component.getMaiorY()-border*2; j+=step) {

				for (int i = component.getMenorX()+border; i < component.getMaiorX()-border*2; i+=step) {

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
	public boolean validateComponent(Componente component){

		boolean result = false;

		if((component.getNumeroPontos()>=100)&&(component.getW()<w/6)){
			result = true;
		}

		return result;
	}


}
