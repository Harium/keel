package com.harium.keel.modifier.edge;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.PointFeature;
import com.harium.etyl.linear.Point2D;

import java.util.List;

public interface EdgeModifier {

    List<Point2D> modify(ImageSource source, PointFeature component);

}
