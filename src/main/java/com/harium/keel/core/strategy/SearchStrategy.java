package com.harium.keel.core.strategy;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;

import java.util.List;


public interface SearchStrategy {
    Component filterFirst(ImageSource source, Component component);

    List<Component> filter(ImageSource source, Component component);
}
