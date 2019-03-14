package com.harium.keel.helper;

import com.harium.etyl.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PointListHelper {

	public static List<Point2D> cloneList(List<Point2D> points){
		
		List<Point2D> clone = new ArrayList<Point2D>();
		
		for(Point2D point: points){
			clone.add(new Point2D(point.x, point.y));
		}
		
		return clone;
		
	}
	
}
