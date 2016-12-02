package examples.medium.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.core.source.BufferedImageSource;
import br.com.etyllica.motion.custom.AverageColorFilter;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.filter.TrackingByDarkerColorFilter;
import br.com.etyllica.motion.filter.validation.MaxDimensionValidation;
import br.com.etyllica.motion.filter.validation.MinDensityValidation;
import br.com.etyllica.motion.filter.validation.MinDimensionValidation;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;

public class MelanomaFinderApplication extends Application {

	private BufferedImage buffer;
	private BufferedImageSource source = new BufferedImageSource();

	private TrackingByDarkerColorFilter skinFilter;

	private Component screen;

	private Component biggestComponent;

	private List<Component> candidates;
	private List<Point2D> list;

	private Color averageSkinColor;

	private HullComponent convexHull;

	private boolean hide = false;
		
	private Color avgBiggestComponentColor;

	public MelanomaFinderApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		//Load the image with elements
		buffer = new BufferedLayer("melanoma/melanoma1.png").getBuffer();
		source.setImage(buffer);

		//Process image to calculate it's Average Color
		AverageColorFilter avgColorFilter = new AverageColorFilter();

		averageSkinColor = avgColorFilter.process(buffer);

		int width = buffer.getWidth();
		int height = buffer.getHeight();

		//Define the area to search for elements
		screen = new Component(0, 0, width, height);

		//Define skin filter
		skinFilter = new TrackingByDarkerColorFilter(w, h, averageSkinColor, 80);

		//Define validations
		skinFilter.addValidation(new MinDensityValidation(50)); //Components must have at least 50% of pixel density  
		skinFilter.addValidation(new MinDimensionValidation(20)); //Components should be bigger than 20x20px 
		skinFilter.addValidation(new MaxDimensionValidation(width/2)); //Components should be smaller than (width/2)x(width/2)px

		loading = 80;

		loadingInfo = "Start Filter";

		//Search for melanoma candidates
		candidates = skinFilter.filter(source, screen);

		//Find the biggest component/candidate
		biggestComponent = findBiggestComponent(candidates);

		FastConvexHullModifier convexHullModifier = new FastConvexHullModifier();

		//Apply QuickHull Modifier in the biggest component
		convexHull = convexHullModifier.modify(biggestComponent);
		list = convexHull.asList();
		
		//Creates a new avgColorFilter
		avgColorFilter = new AverageColorFilter();
		
		//Calculate melonama's average color
		avgBiggestComponentColor = avgColorFilter.process(buffer, biggestComponent);
		
		loadingInfo = "Filter Complete";
	}

	private Component findBiggestComponent(List<Component> components) {

		Component biggestComponent = candidates.get(0);

		int biggestArea = 0;

		for(int i=0;i<candidates.size(); i++) {

			Component candidate = candidates.get(i);

			if(candidate.getArea() > biggestArea) {
				biggestComponent = candidate;
				biggestArea = candidate.getArea();
			}
		}

		return biggestComponent;
	}

	@Override
	public void draw(Graphics g) {
		g.setAlpha(100);
		g.drawImage(buffer, 0, 0);

		g.setAlpha(50);

		//Draw a black rectangle around the skin components
		for(Component candidate : candidates) {
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.BLACK);
			g.drawPolygon(candidate.getBoundingBox());

			if(!hide){
				drawComponentPixels(g, candidate);	
			}
		}
		
		//Draw Biggest Component
		g.setColor(Color.BLUE);
		g.drawPolygon(biggestComponent.getBoundingBox());

		if(!hide) {
			g.setAlpha(50);

			drawComponentPixels(g, biggestComponent);
			drawConvexHullMask(g, biggestComponent);
		}
		
		g.setAlpha(100);
		
		//Draw Information in the top-left corner
		
		//Draw average skin color Rectangle
		g.setColor(averageSkinColor);
		g.fillRect(0, 40, 40, 30);
		
		g.setColor(Color.WHITE);
		g.drawShadow(45, 60, "← avg skin color");
		
		//Draw average melanoma color rectangle
		g.setColor(avgBiggestComponentColor);
		g.fillRect(0, 86, 40, 30);
		
		g.setColor(Color.WHITE);
		g.drawShadow(45, 106, "← avg melanoma color");
		
		//How to show/hide pixel mask 
		g.writeX(380, "Press H to show/hide colored pixels");

	}

	private void drawComponentPixels(Graphics g, Component component) {

		for(Point2D point: component.getPoints()) {
			g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);
		}
	}

	private void drawConvexHullMask(Graphics g, Component component) {

		Point2D centroid = component.getCenter();

		for(Point2D point: list) {
			g.drawLine(point, centroid);

			g.setColor(Color.RED);
			g.fillCircle(point, 5);
			g.setColor(Color.BLACK);
			g.drawCircle(point, 5);
		}
		
		g.setColor(SVGColor.GHOST_WHITE);
		g.drawPolygon(convexHull.getPolygon());
	}

	@Override
	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_H)) {
			hide = !hide;
		}
	}

}
