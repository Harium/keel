package com.harium.keel.core.strategy;

import com.harium.keel.feature.PointFeature;

public interface ComponentModifierStrategy {

	PointFeature modifyComponent(PointFeature component);
	
}
