package com.harium.keel.feature.trail;

public enum RangeFlag {
	POSITIVE, NEGATIVE, NEUTRAL;
	
	private RangeFlag opposite;
	
	static {
		POSITIVE.opposite = NEGATIVE;
		NEGATIVE.opposite = POSITIVE;
	}
	
	public boolean isOpposite(RangeFlag flag) {
		if(this != NEUTRAL) {
			return opposite == flag;
		}
		
		return false;
	}
		
}
