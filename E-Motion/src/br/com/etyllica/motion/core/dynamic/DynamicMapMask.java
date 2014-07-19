package br.com.etyllica.motion.core.dynamic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicMapMask implements DynamicMask {

	Map<Integer, Set<Integer>> isInvalid = new HashMap<Integer, Set<Integer>>();
	
	Map<Integer, Set<Integer>> isValid = new HashMap<Integer, Set<Integer>>();

	@Override
	public boolean isUnknown(int px, int py) {
	
		boolean isValidPoint = isValid(px, py);
		
		boolean isInvalidPoint = isValid(px, py);
		
		return !isValidPoint&&!isInvalidPoint;
	}

	@Override
	public boolean isTouched(int px, int py) {

		return !isUnknown(px, py);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {

		isValid.clear();
		
		isInvalid.clear();
	}

	@Override
	public void setTouched(int px, int py) {
		// TODO Auto-generated method stub
		
	}	
	
}
