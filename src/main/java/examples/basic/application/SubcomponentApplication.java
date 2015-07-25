package examples.basic.application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.etyllica.awt.AWTGraphics;
import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.validation.MaxDimensionValidation;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;

public class SubcomponentApplication extends Application {

	private BufferedImage image;

	private ColorFilter whiteFilter;
	private ColorFilter blackFilter;

	private FastConvexHullModifier modifier;

	private Component screen;

	private List<Component> whiteComponents;
	private Map<Integer, List<Component>> subComponents;

	public SubcomponentApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		subComponents = new HashMap<Integer, List<Component>>();

		//Define the area to search for elements
		screen = new Component(0, 0, w, h);

		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		//Create the image with elements
		drawImage(image);

		//Define white and black filters
		whiteFilter = new ColorFilter(w, h, Color.WHITE);
		whiteFilter.getSearchStrategy().addValidation(new MaxDimensionValidation(w/2));

		blackFilter = new ColorFilter(w, h, Color.BLACK);

		//Filter the image 
		whiteComponents = whiteFilter.filter(image, screen);

		modifier = new FastConvexHullModifier();

		HullComponent hull = modifier.modify(whiteComponents.get(0));
		List<Component> sub = blackFilter.filter(image, hull);

		subComponents.put(0, sub);
	}

	private void drawImage(BufferedImage image) {

		Graphic g = new AWTGraphics(image);

		g.setColor(Color.WHITE);

		g.fillRect(0, 0, w, h);

		g.setColor(Color.BLACK);

		g.setBasicStroke(8);

		g.drawLine(40, 40, 140, 40);
		g.drawLine(40, 40, 40, 100);
		g.drawLine(140, 40, 40, 100);

		g.drawOval(180, 40, 100, 80);

		g.drawRect(300, 200, 80, 80);

		//Draw Subcomponents
		g.setBasicStroke(1);
		g.fillCircle(60, 60, 5);
		g.fillCircle(80, 60, 5);

	}

	@Override
	public void draw(Graphic g) {
		g.setAlpha(100);
		g.drawImage(image, 0, 0);

		g.setAlpha(90);

		//Count subComponents
		for(Component component: whiteComponents) {
			//Draw a red line around the hull of white components
			g.setStroke(new BasicStroke(3f));
			g.setColor(Color.RED);
			g.drawPolygon(component.getBoundingBox());
		}

		for(List<Component> list:subComponents.values()) {
			for(Component subcomponent: list) {
				g.setStroke(new BasicStroke(3f));
				g.setColor(SVGColor.BLUE_VIOLET);
				g.drawPolygon(subcomponent.getBoundingBox());
			}
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
