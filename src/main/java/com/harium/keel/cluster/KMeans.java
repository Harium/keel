package com.harium.keel.cluster;

import com.harium.keel.cluster.distance.DistanceMeasure;
import com.harium.keel.cluster.distance.EuclideanDistance;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class KMeans<T> implements ClusterFit<T> {

    private static final Random random = new Random();

    private int k;
    private int maxIterations;

    private DistanceMeasure distanceMeasure = new EuclideanDistance();

    public KMeans<T> k(int k) {
        this.k = k;
        return this;
    }

    public KMeans<T> maxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
        return this;
    }

    public KMeans<T> distance(DistanceMeasure distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
        return this;
    }

    @Override
    public List<Cluster<T>> fit(List<Record<T>> points) {
        List<Centroid<T>> centroids = randomCentroids(points, k);
        Map<Centroid<T>, List<Record<T>>> clusters = new HashMap<>();
        Map<Centroid<T>, List<Record<T>>> lastState = new HashMap<>();

        // iterate for a pre-defined number of times
        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (Record<T> record : points) {
                Centroid<T> centroid = nearestCentroid(record, centroids, distanceMeasure);
                assignToCluster(clusters, record, centroid);
            }

            // if the assignments do not change, then the algorithm terminates
            boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
            lastState = clusters;
            if (shouldTerminate) {
                break;
            }

            // at the end of each iteration we should relocate the centroids
            centroids = relocateCentroids(clusters);
            clusters = new HashMap<>();
        }

        List<Cluster<T>> result = new ArrayList<>();
        for (Map.Entry<Centroid<T>, List<Record<T>>> entry : lastState.entrySet()) {
            Cluster<T> cluster = new Cluster<>();
            cluster.getRecords().addAll(entry.getValue());
            cluster.centroid = entry.getKey();
            result.add(cluster);
        }
        return result;
    }

    private List<Centroid<T>> randomCentroids(List<Record<T>> records, int k) {
        List<Centroid<T>> centroids = new ArrayList<>();

        int len = records.get(0).getFeatures().length;

        double[] maxs = new double[len];
        double[] mins = new double[len];

        for (Record<T> record : records) {
            for(int i=0;i<len;i++) {
                double value = record.getFeatures()[i];
                if (value > maxs[i]) {
                    maxs[i] = value;
                }
                if (value < mins[i]) {
                    mins[i] = value;
                }
            }
        }

        for (int i = 0; i < k; i++) {
            double[] coordinates = new double[len];
            for (int j = 0; j < len; j++) {
                double max = maxs[j];
                double min = mins[j];

                coordinates[j] = random.nextDouble() * (max - min) + min;
            }
            centroids.add(new Centroid<T>(coordinates));
        }

        return centroids;
    }

    private Centroid<T> nearestCentroid(Record<T> record, List<Centroid<T>> centroids, DistanceMeasure distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid<T> nearest = null;

        for (Centroid<T> centroid : centroids) {
            double currentDistance = distance.distance(record.getFeatures(), centroid.features);

            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    private void assignToCluster(Map<Centroid<T>, List<Record<T>>> clusters,
                                        Record<T> record,
                                        Centroid<T> centroid) {
        clusters.compute(centroid, (key, list) -> {
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(record);
            return list;
        });
    }

    private Centroid<T> average(Centroid<T> centroid, List<Record<T>> records) {
        if (records == null || records.isEmpty()) {
            return centroid;
        }

        double[] average = centroid.features;
        Arrays.fill(average, 0);

        for (Record<T> record : records) {
            for (int i = 0; i < average.length; i++) {
                average[i] += record.getFeatures()[i];
            }
        }

        for (int i = 0; i < average.length; i++) {
            average[i] /= records.size();
        }

        return new Centroid<T>(average);
    }

    private List<Centroid<T>> relocateCentroids(Map<Centroid<T>, List<Record<T>>> clusters) {
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());
    }

}
