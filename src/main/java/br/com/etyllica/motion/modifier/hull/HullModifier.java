package br.com.etyllica.motion.modifier.hull;

import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.feature.Component;

public interface HullModifier<T> extends ComponentModifierStrategy, ComponentModifier<Component, T> {

}
