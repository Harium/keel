package com.harium.keel.filter.process;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.PointFeature;

public interface ProcessComponentFilter<T> {

    T process(ImageSource source, PointFeature component);

}
