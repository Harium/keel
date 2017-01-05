package br.com.etyllica.motion.modifier.ar;

import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;
import br.com.etyllica.motion.modifier.hull.HullModifier;
import br.com.etyllica.motion.modifier.smooth.PathCompressionModifier;

public class CombinedARModifier implements HullModifier<List<Point2D>> {

	final HullModifier<HullComponent> hull;
	final PathCompressionModifier smooth;
	
	public CombinedARModifier() {
		super();
		
		hull = new FastConvexHullModifier();
		
		smooth = new PathCompressionModifier();
		smooth.setMinAngle(30);
	}
	
	@Override
	public Component modifyComponent(Component component) {
		hull.modifyComponent(component);
		return component;
	}

	@Override
	public List<Point2D> modify(Component component) {
		HullComponent modified = hull.modify(component);
		List<Point2D> points = smooth.modify(modified);
		
		//Swap last points
		Point2D c = points.get(2);
		points.remove(2);
		points.add(c);
		
		return points;
	}

}
