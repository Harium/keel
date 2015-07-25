package examples.basic.application;

import java.awt.Color;
import java.awt.Polygon;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;

public class QuickHullExampleApplication extends Application {

	private FastConvexHullModifier quickHullModifier;
	
	private Component component;
	
	private HullComponent convexHull = null;
	
	public QuickHullExampleApplication(int w, int h) {
		super(w, h);//Size of our application
	}
		
	@Override
	public void load() {

		//listOfPoints = new ArrayList<Point2D>();
		component = new Component();
		
		quickHullModifier = new FastConvexHullModifier();
		
		loading = 100;
	}

	@Override
	public void draw(Graphic g) {
		
		g.setColor(Color.BLACK);
		
		g.setFontSize(18);
		
		g.writeX(40, "Quick Hull Example");
		
		g.writeX(70, "Press Left Mouse Button to Add Points");
		
		g.writeX(90, "Press Right Mouse Button to Compute the Convex Hull");
		
		for(Point2D point: component.getPoints()) {
			g.drawCircle(point, 5);
		}
		
		drawConvexHull(g);
		
	}
	
	private void drawConvexHull(Graphic g) {

		if(convexHull==null)
			return;
		
		Polygon polygon = convexHull.getPolygon();
		
		g.setColor(SVGColor.BLACK);
		
		for(Point2D point: convexHull.asList()) {
			g.drawCircle(point, 5);
		}
		
		g.setColor(SVGColor.KHAKI);
		g.drawPolygon(polygon);
		g.setColor(SVGColor.BLACK);
		g.drawPolygon(polygon);
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)){
			//Add a new Point to the list
			component.add(new Point2D(event.getX(), event.getY()));
		}
		
		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)){
			//Compute the Convex Hull
			convexHull = quickHullModifier.modify(component);
		}
		
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
				
		// TODO Auto-generated method stub
		return null;
	}	
	
}
