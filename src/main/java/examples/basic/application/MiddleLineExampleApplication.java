package examples.basic.application;

import java.awt.Color;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.modifier.MiddleLineModifier;
import br.com.etyllica.linear.graph.GenericEdge;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;

public class MiddleLineExampleApplication extends Application {

	private Component component;
	
	private Graph<Integer> graph;
	
	private MiddleLineModifier modifier;
		
	public MiddleLineExampleApplication(int w, int h) {
		super(w, h);//Size of our application
	}
		
	@Override
	public void load() {

		component = new Component();
		
		component.add(203,252);
		component.add(255,226);
		component.add(313,309);
		component.add(351,211);
		component.add(409,224);
		component.add(364,313);
		component.add(473,240);
		component.add(512,277);
		component.add(359,361);
		component.add(424,449);
		component.add(360,457);
		component.add(314,367);
		component.add(256,443);
		component.add(196,412);
		component.add(268,339);
		//Repeat first Point
		component.add(203,252);
				
		modifier = new MiddleLineModifier();
				
		graph = modifier.modify(component);
		
		loading = 100;
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		
		g.setFontSize(18);
		
		g.drawStringX("Middle Line Example", 40);
		
		g.drawStringX("Press Left Mouse Button to Add Points", 70);
		
		g.drawStringX("Press Right Mouse Button to Compute the Middle Line", 90);
				
		drawPolygon(g);
		
		drawGraph(g);
		
	}
	
	private void drawGraph(Graphics g) {
		
		g.setColor(Color.BLUE);
		
		for(GenericEdge<Integer> edge: graph.getEdges()) {
			g.drawLine(edge.getOrigin().getPoint(), edge.getDestination().getPoint());
		}
		
		for(Node<Integer> node: graph.getNodes()) {
						
			g.setColor(Color.WHITE);
			
			g.fillCircle(node.getPoint(), 5);

			g.setColor(Color.BLACK);
			
			g.drawCircle(node.getPoint(), 5);
		}		
		
	}
	
	private void drawPolygon(Graphics g) {

		if(component.getPoints().isEmpty())
			return;
		
		Point2D firstPoint = component.getPoints().get(0);
		
		//Avoiding Null Pointer
		Point2D lastPoint = firstPoint;
		
		for(Point2D point: component.getPoints()) {
			
			g.drawLine(point, lastPoint);
			
			g.drawCircle(point, 5);

			lastPoint = point;
			
		}
		
		for(int i=0; i<component.getPoints().size()-1; i++) {
			
			Point2D a = component.getPoints().get(i);
			Point2D b = component.getPoints().get(i+1);
			
			String text = MiddleLineModifier.classify(a, b).toString();
			g.drawStringShadow(text, (int)(a.getX()+b.getX())/2, (int)(a.getY()+b.getY())/2);
		}
		
		g.drawLine(lastPoint, firstPoint);
		
	}

	@Override
	public void updateMouse(PointerEvent event) {
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)){
			//Add a new Point to the list
			component.add(event.getX(), event.getY());
		}
		
		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_RIGHT)){
			//Compute the Middle Line
		}
	}
	
}
