package br.com.etyllica.motion.modifier.ogr;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.ogr.LineInterval;

public class LineIntervalModifier implements ComponentModifier<Component, List<LineInterval>> {

	private int step = 1;
	
	public List<LineInterval> modify(Component component) {
		
		boolean[][] mask = component.generateMask();
				
		int width = component.getW();
		int height = component.getH();

		List<LineInterval> intervals = new ArrayList<LineInterval>();
		
		for (int j = 0; j < height; j += step) {
			
			LineInterval currentInterval = null;
			
			for (int i = 0; i < width; i += step) {
				if(!component.isInside(i, j)) {
					continue;
				}
								
				if (mask[i][j]) {
					//Start Interval
					if(currentInterval == null) {
						currentInterval = new LineInterval(i, 1);
					} else {
						currentInterval.expand();
					}
					
				} else {
					//End Interval
					intervals.add(currentInterval);
					currentInterval = null;
				}
			}
		}

		return intervals;
	}
	
}
