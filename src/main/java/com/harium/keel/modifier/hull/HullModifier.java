package com.harium.keel.modifier.hull;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.strategy.ComponentModifierStrategy;
import com.harium.keel.feature.Component;

public interface HullModifier<T> extends ComponentModifierStrategy, Modifier<Component, T> {

}
