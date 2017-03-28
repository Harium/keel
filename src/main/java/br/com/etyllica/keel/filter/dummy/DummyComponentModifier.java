package br.com.etyllica.keel.filter.dummy;

import br.com.etyllica.keel.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.keel.feature.Component;

public class DummyComponentModifier implements ComponentModifierStrategy {

	@Override
	public Component modifyComponent(Component component) {

		return component;
	}
	
}
