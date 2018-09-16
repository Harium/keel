package com.harium.keel.core.loader;

import com.harium.keel.core.source.ImageSource;

public interface Loader {
    ImageSource load(String path);
}
