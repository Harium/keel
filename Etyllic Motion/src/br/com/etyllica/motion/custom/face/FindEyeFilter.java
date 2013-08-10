package br.com.etyllica.motion.custom.face;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Componente;
import br.com.etyllica.motion.filter.BooleanMaskFilter;

public class FindEyeFilter extends BooleanMaskFilter{

	public FindEyeFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public List<Componente> filter(BufferedImage bimg, List<Componente> components) {

		resetMask();

		List<Componente> componentes = new ArrayList<Componente>();
		
		for(Componente face: components){
			
			int w = face.getMaiorX();
			int h = face.getMaiorY();

			//TODO Verify if face.getMenorY() ~ face.getMaiorY()
			for (int j = face.getMenorY(); j < h; j++) {

				for (int i = face.getMenorX(); i < w; i++) {

					if (validateColor(bimg.getRGB(i,j)) && !mask[i][j]) {

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

								}
							}
						}

						//Componentes de cada Grupo
						if(validateComponent(lista)){
							componentes.add(lista);
						}

					}
				}
			}
		}

		return componentes;

	}

	@Override
	protected boolean validateColor(int rgb){
		return isBlack(rgb);
	}

	@Override
	protected boolean validateComponent(Componente component){
		return component.getNumeroPontos()>50;
	}

}
