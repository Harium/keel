package examples.filter.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.loader.image.ImageLoader;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.image.LensCorrectionFilter;
import br.com.etyllica.motion.filter.image.SimpleFishEyeCorrectionFilter;
import br.com.etyllica.motion.filter.search.FloodFillSearch;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;
import br.com.etyllica.motion.modifier.hull.HullModifier;

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
		BufferedImage corrected = new SimpleFishEyeCorrectionFilter(-0.90).process(image);
		//BufferedImage corrected = new LensCorrectionFilter().process(image);
		
		
		layer = new BufferedLayer(corrected);
		layer.resize(640, 480);
		
		loading = 50;

	}
	
	@Override
	public void draw(Graphic g) {
		layer.draw(g);
	}
	
}
