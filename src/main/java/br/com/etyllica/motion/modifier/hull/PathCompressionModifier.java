package br.com.etyllica.motion.modifier.hull;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.util.EtyllicaMath;

public class PathCompressionModifier implements ComponentModifier<HullComponent, List<Point2D>> {

	private double minAngle = 5;

	public PathCompressionModifier() {
		super();
	}
	
	public PathCompressionModifier(double minAngle) {
		super();
		
		this.minAngle = minAngle;
	}
	
	@Override
	public List<Point2D> modify(HullComponent component) {
		List<Point2D> points = component.getPoints();
		
		List<Point2D> list = new ArrayList<Point2D>();
		
		if(points.size() <= 2) {
			list.addAll(points);
		}

		int needleIndex = 0;
		double lastAngle = 0;

		list.add(points.get(0));

		Point2D lastNeedle = points.get(needleIndex);
		Point2D point = points.get(needleIndex+1);
		lastAngle = lastNeedle.angle(point);

		for(int i = 2; i < points.size(); i++) {
			point = points.get(i);

			double angle = lastNeedle.angle(point);

			if ((EtyllicaMath.diffMod(angle, lastAngle) >= minAngle) && (i <= points.size()-1)) {
				lastNeedle = points.get(i-1);
				list.add(lastNeedle);
				lastAngle = lastNeedle.angle(points.get(i));
			}
		}

		list.add(point);

		return list;
	}

	public double getMinAngle() {
		return minAngle;
	}

	public void setMinAngle(double minAngle) {
		this.minAngle = minAngle;
	}
}
