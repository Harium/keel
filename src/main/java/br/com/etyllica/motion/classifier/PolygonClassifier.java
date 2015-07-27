package br.com.etyllica.motion.classifier;

import java.util.List;

import br.com.etyllica.core.linear.Point2D;

public class PolygonClassifier {

	public static String indentifyRegionByNumberOfPoints(int points) {
		String form = "undefined";

		switch(points) {

		case 3:
			form = "triangle";
			break;

		case 4:
			form = "rectangle";
			break;
			
		case 5:
			form = "pentagon";
			break;
			
		case 6:
			form = "hexagon";
			break;

		default:
			form = "circle";
			break;
		}
		
		return form;
	}
	
	public static String indentifyRegion(List<Point2D> list) {
		int numberOfPoints = list.size();
		return indentifyRegionByNumberOfPoints(numberOfPoints);
	}
	
}
