package br.com.etyllica.motion.modifier.ogr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.ogr.LineInterval;

public class LineIntervalModifier implements ComponentModifier<Component, List<LineInterval>> {

	private int step = 1;
	
	public List<LineInterval> modify(boolean[][] mask) {
		if(mask == null || mask.length == 0) {
			return null;
		}
		
		int h = mask.length;
		int w = mask[0].length;
		
		List<LineInterval> intervals = new ArrayList<LineInterval>();
		
		for (int j = 0; j < h; j += step) {
			
			LineInterval currentInterval = null;
			
			for (int i = 0; i < w; i += step) {
				if (mask[j][i]) {
					//Start Interval
					if(currentInterval == null) {
						currentInterval = new LineInterval(i, 1, j);
						continue;
					} else {
						currentInterval.expand();
					}
					
				} else if(currentInterval != null) {
					//End Interval
					intervals.add(currentInterval);
					currentInterval = null;
				}
			}
			
			if(currentInterval != null) {
				//End Interval
				intervals.add(currentInterval);
				currentInterval = null;
			}
		}

		return intervals;
	}
	
	public List<LineInterval> modify(Component component) {
		boolean[][] mask = component.generateMask();
		return modify(mask);
	}
	
	public static Map<Integer, List<LineInterval>> groupIntervals(List<LineInterval> intervals) {
		
		Map<Integer, List<LineInterval>> map = new HashMap<Integer, List<LineInterval>>();
		
		for(LineInterval interval: intervals) {
			int key = interval.getHeight();
			if(!map.containsKey(key)) {
				map.put(key, new ArrayList<LineInterval>());
			}
			
			map.get(key).add(interval);
		}
		
		return map;
	}
	
}
