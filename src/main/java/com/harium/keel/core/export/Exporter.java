package com.harium.keel.core.export;

import com.harium.keel.core.source.ImageSource;

public interface Exporter<T> {
    T export(ImageSource source);
}
