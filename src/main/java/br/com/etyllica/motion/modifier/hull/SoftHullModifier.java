package br.com.etyllica.motion.modifier.hull;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.util.EtyllicaMath;

public class SoftHullModifier extends FastConvexHullModifier {

	private double minAngle = 15;
	
	@Override
	protected ArrayList<Point2D> quickHullList(List<Point2D> rawList) {
		
		ArrayList<Point2D> list = super.quickHullList(rawList);
		
		int size = list.size();
		
		Point2D lastPoint = list.get(size-1);
		double lastAngle = lastPoint.angle(list.get(size-2));
		double lastDiff = 0;
		
		for (int i = size-2; i >= 0; i--) {
			
			Point2D point = list.get(i);
			
			double currentAngle = point.angle(lastPoint);
			double diff = EtyllicaMath.diffMod(lastAngle, currentAngle);
			
			if(diff > 180) {
				diff -= 360;
			}
			
			if ((diff < minAngle)||EtyllicaMath.diffMod(diff,lastDiff) < minAngle) {
				System.out.println("REMOVE: "+currentAngle+"/"+diff);
				list.remove(i);
			} else {
				System.out.println(currentAngle+"/"+diff);
			}
			
			lastAngle = currentAngle;
			lastPoint = point;
			lastDiff = diff;
		}
		
		return list;
	}
	
}
