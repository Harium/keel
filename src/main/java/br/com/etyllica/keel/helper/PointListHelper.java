package br.com.etyllica.keel.helper;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.linear.Point2D;

public class PointListHelper {

	public static List<Point2D> cloneList(List<Point2D> points){
		
		List<Point2D> clone = new ArrayList<Point2D>();
		
		for(Point2D point: points){
			clone.add(new Point2D(point.getX(), point.getY()));
		}
		
		return clone;
		
	}
	
}
