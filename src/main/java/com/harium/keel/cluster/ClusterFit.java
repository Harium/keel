package com.harium.keel.cluster;

import java.util.List;

public interface ClusterFit<T> {

    List<Cluster<T>> fit(List<Record<T>> data);

}
