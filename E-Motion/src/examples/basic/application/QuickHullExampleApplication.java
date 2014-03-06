package examples.basic.application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.filter.modifier.QuickHullModifier;

public class QuickHullExampleApplication extends Application {

	private QuickHullModifier quickHullModifier;
	
	private List<Point2D> listOfPoints;
	
	private List<Point2D> convexHull = null;
	
	public QuickHullExampleApplication(int w, int h) {
		super(w, h);//Size of our application
	}
		
	@Override
	public void load() {

		listOfPoints = new ArrayList<Point2D>();
		
		quickHullModifier = new QuickHullModifier();
		
		loading = 100;
	}

	@Override
	public void draw(Graphic g) {
		
		g.setColor(Color.BLACK);
		
		g.setFontSize(18);
		
		g.writeX(40, "Quick Hull Example");
		
		g.writeX(70, "Press Left Mouse Button to Add Points");
		
		g.writeX(90, "Press Right Mouse Button to Compute the ConvexHull");
		
		for(Point2D point: listOfPoints){
			g.drawCircle(point, 5);
		}
		
		drawConvexHull(g);
		
	}
	
	private void drawConvexHull(Graphic g) {

		if(convexHull==null)
			return;
		
		Point2D firstPoint = convexHull.get(0);
		
		//Avoiding Null Pointer
		Point2D lastPoint = firstPoint;
		
		for(Point2D point: convexHull){
			
			g.drawLine(point, lastPoint);
			
			g.drawCircle(point, 5);
						
			lastPoint = point;
			
		}
		
		g.drawLine(lastPoint, firstPoint);
		
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)){
			//Add a new Point to the list
			listOfPoints.add(new Point2D(event.getX(), event.getY()));
		}
		
		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)){
			//Compute the Convex Hull
			convexHull = quickHullModifier.quickHull(listOfPoints);
		}
		
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
				
		// TODO Auto-generated method stub
		return null;
	}	
	
}
