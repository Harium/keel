package br.com.etyllica.motion.core.dynamic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicMapMask implements DynamicMask {

	private Map<Integer, Set<Integer>> isInvalid = new HashMap<Integer, Set<Integer>>();
	
	private Map<Integer, Set<Integer>> isValid = new HashMap<Integer, Set<Integer>>();
	
	private Map<Integer, Set<Integer>> isTouchedMap = new HashMap<Integer, Set<Integer>>();
	
	private Set<Integer> isLineTouched = new HashSet<Integer>();

	@Override
	public boolean isUnknown(int px, int py) {
	
		boolean isValidPoint = isValid(px, py);
		
		boolean isInvalidPoint = isInvalid(px, py);
		
		return !isValidPoint&&!isInvalidPoint;
	}

	@Override
	public boolean isTouched(int px, int py) {

		if(isLineTouched.contains(px)) {
			
			return true;
			
		} else {
		
			Set<Integer> ySet = isTouchedMap.get(px);
			
			if(ySet != null) {
				return ySet.contains(py);
			}
			
		}
		
		return false;
	}

	@Override
	public boolean isValid(int px, int py) {

		Set<Integer> ySet = isValid.get(px);
		
		if(ySet != null) {
			
			return ySet.contains(py);			
		}
		
		return false;
	}
	
	public boolean isInvalid(int px, int py) {

		Set<Integer> ySet = isInvalid.get(px);
		
		if(ySet != null) {
			
			return ySet.contains(py);			
		}
		
		return false;
	}

	@Override
	public void setValid(int px, int py) {
		
		Set<Integer> ySet = isValid.get(px);
		
		if(ySet == null) {
			
			ySet = new HashSet<Integer>();
			
			isValid.put(px, ySet);			
		}
		
		ySet.add(py);
				
	}

	@Override
	public void setInvalid(int px, int py) {

		Set<Integer> ySet = isInvalid.get(px);
		
		if(ySet == null) {
			
			ySet = new HashSet<Integer>();
			
			isInvalid.put(px, ySet);			
		}
		
		ySet.add(py);
	}

	@Override
	public void reset() {

		isValid.clear();
		
		isInvalid.clear();
	}

	@Override
	public void setTouched(int px, int py) {

		Set<Integer> ySet = isTouchedMap.get(px);
		
		if(ySet == null) {
			
			ySet = new HashSet<Integer>();
			
			isTouchedMap.put(px, ySet);			
		}
		
		/*if(ySet.size() == w-1) {
			//Add to line touched
		}*/
		ySet.add(py);
	}	
	
}
