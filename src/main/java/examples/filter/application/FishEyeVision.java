package examples.filter.application;

import java.awt.image.BufferedImage;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.loader.image.ImageLoader;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.filter.image.SimpleFishEyeCorrectionFilter;

public class FishEyeVision extends Application {

	private BufferedLayer layer;

	public FishEyeVision(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loading = 0;
				
		BufferedImage image = ImageLoader.getInstance().getImage("public_domain/nice-hotel-in-bohol.jpg");
		BufferedImage afterFx = new SimpleFishEyeCorrectionFilter(7.90, 0.55).process(image);
		
		layer = new BufferedLayer(afterFx);
		layer.centralize(this);
		
		loading = 50;
	}
	
	@Override
	public void draw(Graphic g) {
		layer.draw(g);
	}
	
}
