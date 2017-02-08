package br.com.etyllica.motion.modifier.edge;

import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;

public interface EdgeModifier {

	List<Point2D> modify(ImageSource source, Component component);
	
}
