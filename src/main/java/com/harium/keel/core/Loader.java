package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;

public interface Loader {
    ImageSource load(String path);
}
