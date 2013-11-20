package br.com.etyllica.motion.filter.face;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.BooleanMaskFilter;
import br.com.etyllica.motion.features.Component;

public class ExtremelySimpleFindSkinFilter extends BooleanMaskFilter{

	public ExtremelySimpleFindSkinFilter(int w, int h) {
		super(w, h);
	}

	@Override
	protected List<Component> filter(BufferedImage bimg, Component component){

		List<Component> result = new ArrayList<Component>();
		
		for (int j = border; j < h-border*2; j+=step) {

			for (int i = border; i < w-border*2; i+=step) {

				if (validateColor(bimg.getRGB(i,j)) && !mask[i][j]) {

					Queue<Point2D> queue = new LinkedList<Point2D>();
					queue.add(new Point2D(i, j));

					Component lista = new Component(w,h);

					while (!queue.isEmpty()) {
						Point2D p = queue.remove();

						if ((p.getX() >= 0) && (p.getX() < w &&
								(p.getY() >= 0) && (p.getY() < h))) {
							if (!mask[(int)p.getX()][(int)p.getY()] && validateColor(bimg.getRGB((int)p.getX(), (int)p.getY()))) {
								mask[(int)p.getX()][(int)p.getY()] = true;

								lista.add(p);

								queue.add(new Point2D((int)p.getX() + step, (int)p.getY()));
								queue.add(new Point2D((int)p.getX() - step, (int)p.getY()));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() + step));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() - step));

								//queue.add(new Ponto((int)p.getX() + 1, (int)p.getY() + 1));
							}
						}
					}
					//TODO
					//Componentes de cada Grupo

					if(validateComponent(lista)){

						//System.out.println("Blob detected : " + lista.getNumeroPontos() + " pixels.");

						result.add(lista);

					}


				}
			}
		}

		return result;
	}

	@Override
	public boolean validateColor(int rgb) {

		return isSkin(rgb);	

	}

	@Override
	public boolean validateComponent(Component component) {

		boolean sizeok = true;
		//boolean sizeok = component.getH()>component.getW()+component.getW()/6;

		//boolean points = true;
		//boolean points = component.getNumeroPontos()>w*3;
		//boolean points = component.getNumeroPontos()>(w*6)/step;
		boolean points = component.getNumeroPontos()>180;


		return sizeok&&points;
	}

}
