package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.search.SoftFloodFillSearch;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;
import br.com.etyllica.motion.modifier.hull.HullModifier;

public class GeometricFormApplication extends Application {

	private BufferedImage image;

	private ColorFilter blackFilter;
	
	private List<Component> blackComponents;

	private Component screen;

	private HullModifier quickHull;

	private List<String> geometryForm = new ArrayList<String>();
		
	private List<List<Point2D>> convexHull = new ArrayList<List<Point2D>>();

	public GeometricFormApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loading = 0;
		//Define the area to search for elements
		screen = new Component(0, 0, w, h);

		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		loading = 10;
		//Create the image with elements
		drawImage(image);

		//Define blue and black filters
		blackFilter = new ColorFilter(w, h, Color.BLACK);
				
		SoftFloodFillSearch floodFill = (SoftFloodFillSearch)blackFilter.getSearchStrategy();
		floodFill.setStep(1);
		
		loading = 20;
		//Filter the image
		blackComponents = blackFilter.filter(image, screen);

		loading = 25;
		quickHull = new FastConvexHullModifier();
		
		loading = 31;
		
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
			
		case 5:
			form = "Pentagon"; 
			break;
			
		case 6:
			form = "Hexagon"; 
			break;

		default:
			form = "Circle"; 
			break;

		}
		
		form += " "+numberOfPoints;
		
		geometryForm.add(form);
		
		convexHull.add(list);
				
	}

	private void drawImage(BufferedImage image) {

		Graphics2D g = image.createGraphics();
		
		g.setColor(Color.WHITE);

		g.fillRect(0, 0, w, h);

		g.setColor(Color.BLACK);

		g.setStroke(new BasicStroke(6f));
		
		//Draw triangle
		Polygon triangle = new Polygon();
		triangle.addPoint(300, 80);
		triangle.addPoint(200, 180);
		triangle.addPoint(400, 180);
		
		g.drawPolygon(triangle);
				
		g.drawOval(440, 80, 100, 100);

		//Draw Rectangle
		g.drawRect(40, 140, 100, 180);
		
		
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(10), x + w/2, y+h/2);
		g.transform(transform);
		//Draw Rotated Rectangle
		g.drawRect(340, 260, 100, 180);
		
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
			
			g.setStroke(new BasicStroke(1f));
			
			for(Point2D point: convexHull.get(i)) {
			
				g.setColor(Color.BLACK);
				g.drawCircle(point, 5);
			}
			
		}

	}

}
