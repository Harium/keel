package br.com.etyllica.keel.modifier.edge;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import com.harium.etyl.linear.Point2D;

import java.util.List;

public interface EdgeModifier {

    List<Point2D> modify(ImageSource source, Component component);

}
