package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.classifier.ColorClassifier;
import br.com.etyllica.motion.classifier.PolygonClassifier;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.search.flood.SoftFloodFillSearch;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;
import br.com.etyllica.motion.modifier.hull.HullModifier;
import br.com.etyllica.motion.modifier.hull.PathCompressionModifier;

public class ColoredGeometricFormApplication extends Application {

	private BufferedImage image;

	private ColorFilter blackFilter;
	private List<Component> blackComponents;

	private Component screen;

	private HullModifier<HullComponent> quickHull;
	private PathCompressionModifier pathCompressionModifier;

	private List<String> geometryText = new ArrayList<String>();		
	private List<List<Point2D>> convexHull = new ArrayList<List<Point2D>>();

	public ColoredGeometricFormApplication(int w, int h) {
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
		createImage(image);

		//Define blue and black filters
		blackFilter = new ColorFilter(w, h, Color.BLACK);
		
		SoftFloodFillSearch floodFill = (SoftFloodFillSearch)blackFilter.getSearchStrategy();
		floodFill.setStep(1);
		
		loading = 20;
		//Filter the image
		blackComponents = blackFilter.filter(image, screen);

		loading = 25;
		quickHull = new FastConvexHullModifier();
		
		pathCompressionModifier = new PathCompressionModifier(5);
		
		loading = 31;
		
		for(Component component : blackComponents) {
			classifyRegion(component);
		}
		
		loading = 50;
	}
	
	private void classifyRegion(Component region) {
		
		List<Point2D> list = pathCompressionModifier.modify(quickHull.modify(region));
		//List<Point2D> list = quickHull.modify(region).getPoints();
		
		Point2D center = region.getCenter();
		Color color = new Color(image.getRGB((int)center.getX(), (int)center.getY())); 
		
		String colorText = ColorClassifier.getColorName(color.getRed(), color.getGreen(), color.getBlue());
		
		String form = PolygonClassifier.indentifyRegion(list);
		
		String text = colorText+" "+form;
		
		geometryText.add(text);
		convexHull.add(list);
	}

	private void createImage(BufferedImage image) {

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
		
		//Draw Triangle
		g.setColor(Color.BLUE);
		g.fillPolygon(triangle);
		
		g.setColor(Color.BLACK);
		g.drawPolygon(triangle);
				
		//Draw Circle
		g.setColor(Color.YELLOW);
		g.fillOval(440, 80, 100, 100);
		
		g.setColor(Color.BLACK);
		g.drawOval(440, 80, 100, 100);

		//Draw Rectangle
		g.setColor(Color.RED);
		g.fillRect(40, 140, 100, 180);
		
		g.setColor(Color.BLACK);
		g.drawRect(40, 140, 100, 180);
		
		//Draw rotated Rectangle
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(30), x + w/2, y+h/2);
		g.transform(transform);
		
		g.setColor(Color.GREEN);
		g.fillRect(340, 260, 110, 180);
		
		g.setColor(Color.BLACK);
		g.drawRect(340, 260, 110, 180);
		
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
			
			g.writeShadow(geometryText.get(i), component.getRectangle());
			
			g.setStroke(new BasicStroke(1f));
			
			for(Point2D point: convexHull.get(i)) {
				g.setColor(Color.BLACK);
				g.drawCircle(point, 5);
			}	
		}
	}
}
