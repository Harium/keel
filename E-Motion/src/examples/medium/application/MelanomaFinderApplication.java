package examples.medium.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.custom.AverageColorFilter;
import br.com.etyllica.motion.filter.TrackingByDarkerColorFilter;
import br.com.etyllica.motion.filter.modifier.QuickHullModifier;
import br.com.etyllica.motion.filter.validation.ComponentDensityValidation;
import br.com.etyllica.motion.filter.validation.MaxComponentDimension;
import br.com.etyllica.motion.filter.validation.MinComponentDimension;

public class MelanomaFinderApplication extends Application {

	private BufferedImage buffer;

	private TrackingByDarkerColorFilter skinFilter;

	private Component screen;

	private Component biggestComponent;

	private List<Component> candidates;

	private Color averageSkinColor;

	private List<Point2D> convexHull;

	private boolean hide = false;
		
	private Color avgBiggestComponentColor;

	public MelanomaFinderApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		//Create the image with elements
		buffer = new BufferedLayer("melanoma/melanoma1.png").getModifiedBuffer();

		AverageColorFilter avgColorFilter = new AverageColorFilter();
		avgColorFilter.process(buffer);

		averageSkinColor = avgColorFilter.getColor();

		int width = buffer.getWidth();
		int height = buffer.getHeight();

		//Define the area to search for elements
		screen = new Component(0, 0, width, height);

		//Define skin filter
		skinFilter = new TrackingByDarkerColorFilter(w, h, averageSkinColor, 80);

		skinFilter.addValidation(new ComponentDensityValidation(50));
		skinFilter.addValidation(new MinComponentDimension(20));
		skinFilter.addValidation(new MaxComponentDimension(width/2));

		loading = 80;

		loadingPhrase = "Start Filter";

		candidates = skinFilter.filter(buffer, screen);

		biggestComponent = findBiggestComponent(candidates);

		QuickHullModifier convexHullModifier = new QuickHullModifier();

		convexHull = convexHullModifier.quickHull(biggestComponent.getPoints());

		loadingPhrase = "Filter Complete";
		
		//Creates a new avgColorFilter
		avgColorFilter = new AverageColorFilter();
		avgColorFilter.process(buffer, biggestComponent.getPoints());
		
		avgBiggestComponentColor = avgColorFilter.getColor();

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
	public void draw(Graphic g) {
		g.setAlpha(100);
		g.drawImage(buffer, 0, 0);

		g.setAlpha(50);

		//Draw a black line around the skin components
		for(Component candidate : candidates) {

			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.BLACK);
			g.drawPolygon(candidate.getBoundingBox());

			if(!hide){
				drawComponentMask(g, candidate);	
			}

		}
		
		//Draw Biggest Component
		g.setColor(Color.BLUE);
		g.drawPolygon(biggestComponent.getBoundingBox());

		if(!hide) {
			g.setAlpha(50);

			drawComponentMask(g, biggestComponent);
			drawConvexHullMask(g, biggestComponent);
			
			
		}
		
		//Draw averageSkinColor Rect
		g.setAlpha(100);
		g.setColor(averageSkinColor);
		g.fillRect(0, 0, 40, 30);
		
		g.setColor(avgBiggestComponentColor);
		g.fillRect(0, 36, 40, 30);

	}

	private void drawComponentMask(Graphic g, Component component) {

		for(Point2D point: component.getPoints()) {
			g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);
		}

	}

	private void drawConvexHullMask(Graphic g, Component component) {

		Point2D centroid = component.getCenter();

		for(Point2D point: convexHull) {

			g.drawLine(point, centroid);

			g.setColor(Color.RED);
			g.fillCircle(point, 5);
			g.setColor(Color.BLACK);
			g.drawCircle(point, 5);
		}

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_H)) {

			hide = !hide;

		}

		// TODO Auto-generated method stub
		return null;
	}

}
