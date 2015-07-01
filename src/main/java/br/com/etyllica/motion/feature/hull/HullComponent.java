package br.com.etyllica.motion.feature.hull;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.feature.Component;

public class HullComponent extends Component {

	private Polygon polygon;
	
	public HullComponent() {
		super();
		polygon = new Polygon();
	}

	@Override
	public void addLogic(Point2D point) {
		polygon.addPoint((int)point.getX(), (int)point.getY());
	}
	
	@Override
	public boolean isInside(int x, int y) {
		return polygon.contains(x, y);
	}
	
	@Override
	public boolean intersects(int x, int y) {
		return isInside(x, y);
	}
	
	@Override
	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	
	public int getPointCount() {
		return polygon.npoints;
	}
	
	@Override
	public List<Point2D> getPoints() {
		if(points.isEmpty()) {
			for(int i=0; i<polygon.npoints;i++) {
				
				int px = polygon.xpoints[i];
				int py = polygon.ypoints[i];
				
				points.add(new Point2D(px, py));
			}
		}
		
		return points;
	}
	
	public List<Point2D> asList() {
		List<Point2D> list = new ArrayList<Point2D>();
		for(int i = 0; i < polygon.npoints; i++) {
			int x = (int)polygon.xpoints[i];
			int y = (int)polygon.ypoints[i];
			list.add(new Point2D(x, y));
		}
		
		return list;
	}
}
