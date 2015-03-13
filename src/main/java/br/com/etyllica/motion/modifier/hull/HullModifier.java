package br.com.etyllica.motion.modifier.hull;

import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ComponentModifier;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;

public interface HullModifier extends ComponentModifierStrategy, ComponentModifier<List<Point2D>> {

}
