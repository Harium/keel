package br.com.etyllica.motion.hollowcontroller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Componente;
import br.com.etyllica.motion.filter.BooleanMaskFilter;
import br.com.etyllica.motion.filter.Filter;

public class FindRedLedFilter extends BooleanMaskFilter implements Filter{

	public FindRedLedFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public List<Componente> filter(BufferedImage bimg, Componente component) {

		List<Componente> result = new ArrayList<Componente>();

		for (int j = border; j < h-border*2; j+=step) {

			for (int i = border; i < w-border*2; i+=step) {

				if (validateColor(bimg.getRGB(i,j)) && !mask[i][j]) {

					//System.out.println("Find Point ["+i+"]["+j+"]");

					Queue<Ponto2D> queue = new LinkedList<Ponto2D>();
					queue.add(new Ponto2D(i, j));

					Componente lista = new Componente(w,h);

					while (!queue.isEmpty()) {
						Ponto2D p = queue.remove();

						if ((p.getX() >= 0) && (p.getX() < w &&
								(p.getY() >= 0) && (p.getY() < h))) {
							if (!mask[(int)p.getX()][(int)p.getY()] && validateColor(bimg.getRGB((int)p.getX(), (int)p.getY()))) {
								mask[(int)p.getX()][(int)p.getY()] = true;

								lista.add(p);

								queue.add(new Ponto2D((int)p.getX() + 1, (int)p.getY()));
								queue.add(new Ponto2D((int)p.getX() - 1, (int)p.getY()));
								queue.add(new Ponto2D((int)p.getX(), (int)p.getY() + 1));
								queue.add(new Ponto2D((int)p.getX(), (int)p.getY() - 1));


								//queue.add(new Ponto((int)p.getX() + 1, (int)p.getY() + 1));
							}
						}
					}
					//TODO
					//Componentes de cada Grupo

					if(validateComponent(lista)){
						result.add(lista);
					}

				}
			}
		}

		return result;
	}

	public boolean validateColor(int rgb){

		boolean result = false;
		
		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);
		
		boolean rok = r>0x60;
		boolean gok = g>0x20&&g<=0x70; 
		boolean bok = b>0x10&&b<=0x50;

		int difGreen = r-g;
		boolean difg = difGreen	>0&&difGreen>50;
		
		int difBlue = r-b;
		boolean difb = difBlue>0&&difBlue>40;
		
		if(difg&&difb){
			
			//System.out.println(r+">"+g+"&&"+r+">"+g);
			
			if(rok){
			//if(gok&&bok){
			//if(rok&&gok&&bok){

				result = true;
			}
			
		}else{
			/*System.out.println("REJECTED--");
			System.out.println("RED: 0x"+Integer.toString(r, 16));
			System.out.println("GRE: 0x"+Integer.toString(g, 16));
			System.out.println("BLU: 0x"+Integer.toString(b, 16));*/
			
		}


		return result;
	}

	public boolean validateComponent(Componente component){

		boolean result = false;

		if((component.getNumeroPontos()>=120)&&(component.getW()<w/6)){
			result = true;
		}

		return result;
	}


}
