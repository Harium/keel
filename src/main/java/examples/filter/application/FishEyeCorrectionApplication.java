package examples.filter.application;

import java.awt.image.BufferedImage;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.keel.image.SimpleFishEyeCorrectionFilter;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.loader.image.ImageLoader;

public class FishEyeCorrectionApplication extends Application {

	//private BufferedImage image;
	private BufferedLayer layer;

	public FishEyeCorrectionApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loading = 0;
				
		BufferedImage image = ImageLoader.getInstance().getImage("fisheye/398.jpg");
		BufferedImage corrected = new SimpleFishEyeCorrectionFilter(0.90).process(image);
		//BufferedImage corrected = new LensCorrectionFilter().process(image);
		
		
		layer = new BufferedLayer(corrected);
		layer.resize(640, 480);
		
		loading = 50;

	}
	
	@Override
	public void draw(Graphics g) {
		layer.draw(g);
	}
	
}
