package examples.filter.application;

import java.awt.image.BufferedImage;

import br.com.etyllica.commons.context.Application;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.keel.image.SimpleFishEyeCorrectionFilter;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.loader.image.ImageLoader;

public class FishEyeVision extends Application {

	private BufferedLayer layer;

	public FishEyeVision(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loading = 0;
				
		BufferedImage image = ImageLoader.getInstance().getImage("public_domain/nice-hotel-in-bohol.jpg");
		BufferedImage afterFx = new SimpleFishEyeCorrectionFilter(4.90, 0.55).process(image);
		//BufferedImage afterFx = new SimpleFishEyeCorrectionFilter(7.90, 0.55).process(image);
		
		layer = new BufferedLayer(afterFx);
		layer.centralize(this);
		
		loading = 50;
	}
	
	@Override
	public void draw(Graphics g) {
		layer.draw(g);
	}
	
}
