package br.com.etyllica.motion.filter.face;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.BooleanMaskFilter;
import br.com.etyllica.motion.features.Component;

public class FindEyeFilter extends BooleanMaskFilter{

	public FindEyeFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {

		super.setup();

		int w = component.getHighestX();
		int h = component.getHighestY();

		//TODO Verify if component.getMenorY() ~ component.getMaiorY()
		for (int j = component.getLowestY(); j < h; j++) {

			for (int i = component.getLowestX(); i < w; i++) {

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

								queue.add(new Point2D((int)p.getX() + 1, (int)p.getY()));
								queue.add(new Point2D((int)p.getX() - 1, (int)p.getY()));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() + 1));
								queue.add(new Point2D((int)p.getX(), (int)p.getY() - 1));

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
