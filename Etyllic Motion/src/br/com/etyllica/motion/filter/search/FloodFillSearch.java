package br.com.etyllica.motion.filter.search;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.BooleanMaskSearch;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.ComponentPointCount;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;

public class FloodFillSearch extends BooleanMaskSearch{

	private Component lastComponent;
	
	public FloodFillSearch(int w, int h) {
		super(w, h, new SkinColorStrategy(), new ComponentPointCount(180));
	}
	
	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {

		List<Component> list = filter(bimg, component);
		
		if(!list.isEmpty()){
			lastComponent = list.get(0);
		}
		
		return lastComponent;
		
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component){

		super.setup();
		
		for (int j = border; j < h-border*2; j+=step) {

			for (int i = border; i < w-border*2; i+=step) {

				if (!mask[i][j]&&colorStrategy.validateColor(bimg.getRGB(i,j))) {

					Queue<Point2D> queue = new LinkedList<Point2D>();
					queue.add(new Point2D(i, j));

					Component lista = new Component(w,h);

					while (!queue.isEmpty()) {
						Point2D p = queue.remove();

						if ((p.getX() >= 0) && (p.getX() < w &&
								(p.getY() >= 0) && (p.getY() < h))) {
							if (!mask[(int)p.getX()][(int)p.getY()] && colorStrategy.validateColor(bimg.getRGB((int)p.getX(), (int)p.getY()))) {
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

					if(componentStrategy.validateComponent(lista)){

						//System.out.println("Blob detected : " + lista.getNumeroPontos() + " pixels.");

						result.add(componentModifierStrategy.modifyComponent(lista));

					}

				}
			}
		}

		return result;
	}

}
