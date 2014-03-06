package br.com.etyllica.motion.filter.dumb;

import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;

public class DumbComponentModifier implements ComponentModifierStrategy{

	@Override
	public Component modifyComponent(Component component) {

		return component;
	}
	
}
