package br.com.etyllica.motion.filter.dumb;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;

public class DumbComponentModifier implements ComponentModifierStrategy{

	@Override
	public Component modifyComponent(Component component) {

		return component;
	}
	
}
