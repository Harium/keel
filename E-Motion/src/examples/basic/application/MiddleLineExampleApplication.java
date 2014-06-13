package examples.basic.application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Edge;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.motion.modifier.MiddleLineModifier;

public class MiddleLineExampleApplication extends Application {

	private List<Point2D> listOfPoints;
	
	private Graph graph;
	
	private MiddleLineModifier modifier;
		
	public MiddleLineExampleApplication(int w, int h) {
		super(w, h);//Size of our application
	}
		
	@Override
	public void load() {

		listOfPoints = new ArrayList<Point2D>();
		listOfPoints.add(new Point2D(203,252));
		listOfPoints.add(new Point2D(255,226));
		listOfPoints.add(new Point2D(313,309));
		listOfPoints.add(new Point2D(351,211));
		listOfPoints.add(new Point2D(409,224));
		listOfPoints.add(new Point2D(364,313));
		listOfPoints.add(new Point2D(473,240));
		listOfPoints.add(new Point2D(512,277));
		listOfPoints.add(new Point2D(359,361));
		listOfPoints.add(new Point2D(424,449));
		listOfPoints.add(new Point2D(360,457));
		listOfPoints.add(new Point2D(314,367));
		listOfPoints.add(new Point2D(256,443));
		listOfPoints.add(new Point2D(196,412));
		listOfPoints.add(new Point2D(268,339));
		//Repeat first Point
		listOfPoints.add(new Point2D(203,252));
				
		modifier = new MiddleLineModifier();
		
		modifier.middleLine(listOfPoints);
		
		graph = modifier.middleLine(listOfPoints);
		
		loading = 100;
	}

	@Override
	public void draw(Graphic g) {
		
		g.setColor(Color.BLACK);
		
		g.setFontSize(18);
		
		g.writeX(40, "Middle Line Example");
		
		g.writeX(70, "Press Left Mouse Button to Add Points");
		
		g.writeX(90, "Press Right Mouse Button to Compute the Middle Line");
				
		drawPolygon(g);
		
		drawGraph(g);
		
	}
	
	private void drawGraph(Graphic g) {
		
		g.setColor(Color.BLUE);
		
		for(Edge edge: graph.getEdges()) {
			
			g.drawLine(edge.getOrigin(), edge.getDestination());
			
		}
		
		for(Point2D point: graph.getNodes()) {
						
			g.setColor(Color.WHITE);
			
			g.fillCircle(point, 5);

			g.setColor(Color.BLACK);
			
			g.drawCircle(point, 5);
			
		}
		
		
	}
	
	private void drawPolygon(Graphic g) {

		if(listOfPoints.isEmpty())
			return;
		
		Point2D firstPoint = listOfPoints.get(0);
		
		//Avoiding Null Pointer
		Point2D lastPoint = firstPoint;
		
		for(Point2D point: listOfPoints) {
			
			g.drawLine(point, lastPoint);
			
			g.drawCircle(point, 5);

			lastPoint = point;
			
		}
		
		for(int i=0; i<listOfPoints.size()-1; i++) {
			
			Point2D a = listOfPoints.get(i);
			Point2D b = listOfPoints.get(i+1);
			
			g.drawShadow((int)(a.getX()+b.getX())/2, (int)(a.getY()+b.getY())/2, MiddleLineModifier.rate(a, b).toString());
						
		}
		
		g.drawLine(lastPoint, firstPoint);
		
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)){
			//Add a new Point to the list
			listOfPoints.add(new Point2D(event.getX(), event.getY()));
		}
		
		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)){
			//Compute the Middle Line
		}
		
		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
				
		// TODO Auto-generated method stub
		return null;
	}	
	
}
