package examples.basic.application;

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
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.modifier.FastConvexHullModifier;

public class GeometricFormApplication extends Application {

	private BufferedImage image;

	private ColorFilter blackFilter;

	private List<Component> blackComponents;

	private Component screen;

	private FastConvexHullModifier quickHull;

	private List<String> geometryForm = new ArrayList<String>();

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
		blackFilter = new ColorFilter(w, h, Color.BLACK);	

		//Filter the image		
		blackComponents = blackFilter.filter(image, screen);

		quickHull = new FastConvexHullModifier();
		
		loading = 21;
		
		for(Component component : blackComponents) {
			classifyRegion(component);
		}
		
		loading = 50;

	}
	
	private void classifyRegion(Component region) {
		
		List<Point2D> list = quickHull.modify(region);
		
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

	private void drawImage(BufferedImage image) {

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);

		g.fillRect(0, 0, w, h);

		g.setColor(Color.BLACK);

		g.setStroke(new BasicStroke(6f));

		//Draw Rectangle
		g.drawRect(40, 40, 100, 80);
		
		//Draw triangle
		Polygon triangle = new Polygon();
		triangle.addPoint(300, 80);
		triangle.addPoint(200, 180);
		triangle.addPoint(400, 180);
		
		g.drawPolygon(triangle);
				
		g.drawOval(440, 80, 100, 100);

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
			
			g.setColor(Color.CYAN);
			
			for(Point2D point: component.getPoints()) {
				
				g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);
			}
			
			g.writeShadow(geometryForm.get(i), component.getRectangle());
			
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
