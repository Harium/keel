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

		//Load the image with elements
		buffer = new BufferedLayer("melanoma/melanoma1.png").getModifiedBuffer();

		//Process image to calculate it's Average Color
		AverageColorFilter avgColorFilter = new AverageColorFilter();
		avgColorFilter.process(buffer);

		averageSkinColor = avgColorFilter.getColor();

		int width = buffer.getWidth();
		int height = buffer.getHeight();

		//Define the area to search for elements
		screen = new Component(0, 0, width, height);

		//Define skin filter
		skinFilter = new TrackingByDarkerColorFilter(w, h, averageSkinColor, 80);

		//Define validations
		skinFilter.addValidation(new ComponentDensityValidation(50)); //Components must have at least 50% of pixel density  
		skinFilter.addValidation(new MinComponentDimension(20)); //Components should be bigger than 20x20px 
		skinFilter.addValidation(new MaxComponentDimension(width/2)); //Components should be smaller than (width/2)x(width/2)px

		loading = 80;

		loadingPhrase = "Start Filter";

		//Search for melanoma candidates
		candidates = skinFilter.filter(buffer, screen);

		//Find the biggest component/candidate
		biggestComponent = findBiggestComponent(candidates);

		QuickHullModifier convexHullModifier = new QuickHullModifier();

		//Apply QuickHull Modifier in the biggest component
		convexHull = convexHullModifier.quickHull(biggestComponent.getPoints());
		
		//Creates a new avgColorFilter
		avgColorFilter = new AverageColorFilter();
		avgColorFilter.process(buffer, biggestComponent.getPoints());
		
		//Calculate melonama's average color
		avgBiggestComponentColor = avgColorFilter.getColor();
		
		loadingPhrase = "Filter Complete";

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

	private void drawComponentPixels(Graphic g, Component component) {

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

		return null;
	}

}
