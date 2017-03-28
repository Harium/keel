package br.com.etyllica.keel.modifier.edge;

import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;

public interface EdgeModifier {

	List<Point2D> modify(ImageSource source, Component component);
	
}
