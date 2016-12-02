package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.motion.core.source.BufferedImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.ColorFilter;

public class MultipleTrackingApplication extends Application {

	private BufferedImage image;
	private BufferedImageSource source = new BufferedImageSource();
	
	private ColorFilter blueFilter;
	
	private ColorFilter blackFilter;
	
	private Component screen;
	
	private List<Component> blueComponents;
	
	private List<Component> blackComponents;
	
	public MultipleTrackingApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		
		//Define the area to search for elements
		screen = new Component(0, 0, w, h);
		
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				
		//Create the image with elements
		createImage(image);
		source.setImage(image);
		
		//Define blue and black filters
		blackFilter = new ColorFilter(w, h, Color.BLACK);
		
		blueFilter = new ColorFilter(w, h, Color.BLUE);
		
		//Filter the image 
		blueComponents = blueFilter.filter(source, screen);
		
		blackComponents = blackFilter.filter(source, screen);
		
	}
	
	private void createImage(BufferedImage image) {
		
		Graphics2D g = image.createGraphics();
				
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, w, h);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(40, 40, 100, 80);
		
		g.fillOval(160, 40, 100, 80);
		
		g.fillRect(300, 200, 80, 80);
		
		g.setColor(Color.BLUE);
		
		g.fillRect(30, 200, 100, 80);
		
		g.fillRoundRect(440, 20, 20, 200, 50, 20);
		
		g.fillOval(390, 100, 80, 600);
				
	}

	@Override
	public void draw(Graphics g) {
		g.setAlpha(100);
		g.drawImage(image, 0, 0);
		
		g.setAlpha(90);
		
		//Draw a red line around the black components
		for(Component component: blackComponents) {
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.RED);
			g.drawPolygon(component.getBoundingBox());
		}
		
		//Draw a yellow line around the blue components
		for(Component component: blueComponents) {
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.YELLOW);
			g.drawRect(component.getRectangle());
		}
		
	}
	
}
