package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.TrackingByColorFilter;
import br.com.etyllica.motion.modifier.QuickHullModifier;

public class GeometricFormApplication extends Application {

	private BufferedImage image;

	private TrackingByColorFilter blackFilter;

	private List<Component> blackComponents;

	private Component screen;

	private QuickHullModifier quickHull;

	private List<String> geometryForm;

	public GeometricFormApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		//Define the area to search for elements
		screen = new Component(0, 0, w, h);

		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		//Create the image with elements
		drawImage(image);

		//Define blue and black filters
		blackFilter = new TrackingByColorFilter(w, h, Color.BLACK);		

		//Filter the image		
		blackComponents = blackFilter.filter(image, screen);

		quickHull = new QuickHullModifier();

		loading = 20;
		
		geometryForm = new ArrayList<String>();

		loading = 21;
		
		for(Component component : blackComponents) {
			
			List<Point2D> list = quickHull.modify(component);
			
			int numberOfPoints = list.size();

			String form = "undefined";

			switch(numberOfPoints) {

			case 3:
				form = "Triangle"; 
				break;

			case 4:
				form = "Rectangle"; 
				break;

			default:
				form = "Circle"; 
				break;

			}
			
			geometryForm.add(form);

		}
		
		loading = 50;

	}

	private void drawImage(BufferedImage image) {

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);

		g.fillRect(0, 0, w, h);

		g.setColor(Color.BLACK);

		g.setStroke(new BasicStroke(6f));

		g.drawRect(40, 40, 100, 80);		

	}

	@Override
	public void draw(Graphic g) {
		g.setAlpha(100);
		g.drawImage(image, 0, 0);

		g.setAlpha(90);

		//Draw a red line around the black components
		
		for(int i = 0; i < blackComponents.size(); i++) {
			
			Component component = blackComponents.get(i);
			
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.RED);
			g.drawRect(component.getRectangle());
			
			//g.writeShadow(geometryForm.get(i), component.getRectangle());
			
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
