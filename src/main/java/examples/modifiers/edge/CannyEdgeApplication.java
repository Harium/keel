package examples.modifiers.edge;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.modifier.edge.CannyEdgeModifier;
import br.com.etyllica.keel.modifier.edge.EdgeModifier;
import br.com.etyllica.loader.image.ImageLoader;

public class CannyEdgeApplication extends Application {

	private Component screen;
	private ImageSource source;
	private EdgeModifier modifier;
	
	private List<Point2D> result;
	
	public CannyEdgeApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		BufferedImage image = ImageLoader.getInstance().getImage("hand/dorso.jpg");
		source = new BufferedImageSource(image);
		screen = new Component(0, 0, image.getWidth(), image.getHeight());
		
		modifier = new CannyEdgeModifier();
		applyModifier();
	}
	
	private void applyModifier() {
		result = modifier.modify(source, screen);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(this);
		
		g.setColor(Color.WHITE);
		for (Point2D point: result) {
			g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);	
		}
	}

}
