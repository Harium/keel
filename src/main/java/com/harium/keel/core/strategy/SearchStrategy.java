package com.harium.keel.core.strategy;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

import java.util.List;


public interface SearchStrategy<T> {
    T filterFirst(ImageSource source, Feature component);

    List<T> filter(ImageSource source, Feature component);
}
