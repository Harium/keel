package com.harium.keel.filter.dummy;

import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.Component;

public class DummyComponentModifier implements ComponentModifierStrategy {

	@Override
	public Component modifyComponent(Component component) {

		return component;
	}
	
}
