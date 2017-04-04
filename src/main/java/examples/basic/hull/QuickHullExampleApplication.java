package examples.basic.hull;

import java.awt.Color;
import java.awt.Polygon;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.feature.hull.HullComponent;
import br.com.etyllica.keel.modifier.hull.FastConvexHullModifier;

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
	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		
		g.setFontSize(18);
		
		g.drawStringX("Quick Hull Example", 40);
		
		g.drawStringX("Press Left Mouse Button to Add Points", 70);
		
		g.drawStringX("Press Right Mouse Button to Compute the Convex Hull", 90);
		
		for(Point2D point: component.getPoints()) {
			g.drawCircle(point, 5);
		}
		
		drawConvexHull(g);
		
	}
	
	private void drawConvexHull(Graphics g) {

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
	public void updateMouse(PointerEvent event) {
		
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)){
			//Add a new Point to the list
			component.add(new Point2D(event.getX(), event.getY()));
		}
		
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_RIGHT)){
			//Compute the Convex Hull
			convexHull = quickHullModifier.modify(component);
		}
	}
}
