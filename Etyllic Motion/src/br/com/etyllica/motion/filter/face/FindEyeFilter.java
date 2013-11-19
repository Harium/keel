package br.com.etyllica.motion.filter.face;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.core.BooleanMaskFilter;
import br.com.etyllica.motion.features.Component;

public class FindEyeFilter extends BooleanMaskFilter{

	public FindEyeFilter(int w, int h) {
		super(w, h);
	}

	@Override
	protected List<Component> filter(BufferedImage bimg, Component component) {
		
		List<Component> result = new ArrayList<Component>();
					
			int w = component.getMaiorX();
			int h = component.getMaiorY();

			//TODO Verify if component.getMenorY() ~ component.getMaiorY()
			for (int j = component.getMenorY(); j < h; j++) {

				for (int i = component.getMenorX(); i < w; i++) {

					if (validateColor(bimg.getRGB(i,j)) && !mask[i][j]) {

						Queue<Ponto2D> queue = new LinkedList<Ponto2D>();
						queue.add(new Ponto2D(i, j));

						Component lista = new Component(w,h);

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

								}
							}
						}

						//Componentes de cada Grupo
						if(validateComponent(lista)){
							result.add(lista);
						}

					}
				}
			}
		

		return result;

	}

	@Override
	public boolean validateColor(int rgb){
		return isBlack(rgb);
	}

	@Override
	public boolean validateComponent(Component component){
		return component.getNumeroPontos()>50;
	}

}
