package br.com.etyllica.motion.modifier.hull;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.ogr.OGR;
import br.com.etyllica.motion.ogr.RectangularOGR;

public class RectangularOGRModifier implements HullModifier {

	private OGR ogr;
	
	public RectangularOGRModifier() {
		super();
		
		ogr = new RectangularOGR();
	}

	public Component modifyComponent(Component component) {

		Component box = new Component();

		for(Point2D point: modify(component)) {
			box.add(point);
		}

		return box;
	}

	public List<Point2D> modify(Component component) {

		boolean[][] mask = component.generateMask();
		
		Graph graph = ogr.findGraph(mask);
		
		if(graph.getNodes().size() != 4) {
			
			for(int i=graph.getNodes().size(); i<4;i++) {
				graph.addNode(graph.getNodes().get(0));
			}
			
		}
				
		Point2D a = graph.getNodes().get(1);
		Point2D b = graph.getNodes().get(2);
		Point2D c = graph.getNodes().get(0);
		Point2D d = graph.getNodes().get(3);
		
		//isQuadGraph
		if(a.getY() == c.getY()) {
			
			a = graph.getNodes().get(0);
			b = graph.getNodes().get(1);
			c = graph.getNodes().get(3);
			d = graph.getNodes().get(2);
			
		}
						
		List<Point2D> list = new ArrayList<Point2D>();
				
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		
		for(Point2D point : list) {
			point.setOffset(component.getX(), component.getY());
		}
		
		return list;
	}
	
}
