package com.harium.keel.filter.process;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;

public interface ProcessComponentFilter<T> {

    T process(ImageSource source, Component component);

}
