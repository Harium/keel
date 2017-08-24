package com.harium.keel.filter.process;

import com.harium.keel.core.source.ImageSource;

public interface ProcessFilter<T> {

    T process(ImageSource source);

}
