package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;

public interface Effect {

    ImageSource apply(ImageSource input);

}
