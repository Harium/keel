package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.TrackingByColorFilter;

public class MultipleTrackingApplication extends Application {

	private BufferedImage buffer;
	
	private TrackingByColorFilter blueFilter;
	
	private TrackingByColorFilter blackFilter;
	
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

		//Create the image with elements
		createElements(w, h);
		
		//Define blue and black filters
		blackFilter = new TrackingByColorFilter(w, h, Color.BLACK);
		
		blueFilter = new TrackingByColorFilter(w, h, Color.BLUE);		
		
		//Filter the image 
		blueComponents = blueFilter.filter(buffer, screen);
		
		blackComponents = blackFilter.filter(buffer, screen);
		
	}
	
	private void createElements(int w, int h) {
		
		buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = buffer.createGraphics();
				
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, w, h);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(40, 40, 100, 80);
		
		g.fillRect(160, 40, 100, 80);
		
		g.fillRect(300, 200, 80, 80);
		
		g.setColor(Color.BLUE);
		
		g.fillRect(30, 200, 100, 80);
		
		g.fillRoundRect(440, 20, 20, 200, 50, 20);
		
		g.fillOval(390, 100, 80, 600);
				
	}

	@Override
	public void draw(Graphic g) {
		g.setAlpha(100);
		g.drawImage(buffer, 0, 0);
		
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
	
	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
