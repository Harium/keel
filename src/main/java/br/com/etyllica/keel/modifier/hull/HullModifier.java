package br.com.etyllica.keel.modifier.hull;

import br.com.etyllica.keel.core.ComponentModifier;
import br.com.etyllica.keel.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.keel.feature.Component;

public interface HullModifier<T> extends ComponentModifierStrategy, ComponentModifier<Component, T> {

}
