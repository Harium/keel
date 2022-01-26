package com.harium.keel.cluster;

import com.harium.storage.kdtree.KDTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Forked from: http://alvinalexander.com/java/jwarehouse/commons-math3-3.6.1/src/main/java/org/apache/commons/math3/ml/clustering/DBSCANClusterer.java.shtml
 *
 */
public class DBScan<T> implements ClusterFit<T> {

    /** Maximum radius of the neighborhood to be considered. Also called epsilon */
    private double eps = 0.5;

    /** Minimum number of points needed for a cluster. */
    private int minPoints = 1;

    /** Status of a point during the clustering process. */
    private enum PointStatus {
        /** The point has is considered to be noise. */
        NOISE,
        /** The point is already part of a cluster. */
        PART_OF_CLUSTER
    }

    public DBScan() {
        super();
    }

    /**
     * Creates a new instance of a DBSCANClusterer.
     *
     * @param eps maximum radius of the neighborhood to be considered
     * @param minPoints minimum number of points needed for a cluster
     */
    public DBScan(final double eps, final int minPoints) {
        super();

        this.eps = eps;
        this.minPoints = minPoints;
    }

    /**
     * Returns the maximum radius of the neighborhood to be considered.
     * @return maximum radius of the neighborhood
     */
    public double getEps() {
        return eps;
    }

    /**
     * Returns the minimum number of points needed for a cluster.
     * @return minimum number of points needed for a cluster
     */
    public int getMinPoints() {
        return minPoints;
    }

    /**
     * Performs DBSCAN cluster analysis.
     *
     * @param data the points to cluster
     * @return the list of clusters
     */
    public List<Cluster<T>> fit(final List<Record<T>> data) {
        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }

        int length = data.get(0).getFeatures().length;

        final List<Cluster<T>> clusters = new ArrayList<>();
        KDTree<Record<T>> tree = new KDTree<>(length);
        // Should it be Record<T> instead?
        final Map<double[], PointStatus> visited = new HashMap<>();

        // Populate the kdTree
        for (Record<T> row : data) {
        	double[] key = buildKey(row);
        	tree.insert(key, row);
        }

        for (Record<T> row : data) {
            if (visited.get(row.getFeatures()) != null) {
                continue;
            }
            final List<Record<T>> neighbors = getNeighbors(row, tree);
            if (neighbors.size() >= minPoints) {
                // DBSCAN does not care about center points
                final Cluster<T> cluster = new Cluster<T>();
                clusters.add(expandCluster(cluster, row, neighbors, tree, visited));
            } else {
                visited.put(row.getFeatures(), PointStatus.NOISE);
            }
        }

        return clusters;
    }

    private double[] buildKey(Record row) {
        double[] key = new double[row.getFeatures().length];

        for (int i = 0; i < row.getFeatures().length; i++) {
            key[i] = row.getFeatures()[i];
        }
        return key;
    }

    /**
     * Expands the cluster to include density-reachable items.
     *
     * @param cluster Cluster to expand
     * @param point Point to add to cluster
     * @param neighbors List of neighbors
     * @param tree the data set
     * @param visited the set of already visited points
     * @return the expanded cluster
     */
    private Cluster<T> expandCluster(final Cluster<T> cluster,
                                     final Record<T> point,
                                     final List<Record<T>> neighbors,
                                     final KDTree<Record<T>> tree,
                                     final Map<double[], PointStatus> visited) {

        cluster.addRecord(point);

        visited.put(point.getFeatures(), PointStatus.PART_OF_CLUSTER);

        List<Record<T>> seeds = new ArrayList<>(neighbors);

        for (int index = 0; index < seeds.size(); index++) {
            Record<T> current = seeds.get(index);
            PointStatus pStatus = visited.get(current.getFeatures());

            // only check non-visited points
            if (pStatus == null) {
                final List<Record<T>> currentNeighbors = getNeighbors(point, tree);
                if (currentNeighbors.size() >= minPoints) {
                    seeds = merge(seeds, currentNeighbors);
                }
            }

            if (pStatus != PointStatus.PART_OF_CLUSTER) {
                visited.put(current.getFeatures(), PointStatus.PART_OF_CLUSTER);
                cluster.addRecord(current);
            }
        }
        return cluster;
    }

    /**
     * Returns a list of density-reachable neighbors of a {@code point}.
     *
     * @param record the record to look for
     * @param points possible neighbors
     * @return the List of neighbors
     */
    private List<Record<T>> getNeighbors(final Record<T> record, KDTree<Record<T>> points) {
        double[] key = record.getFeatures();
        return new ArrayList<>(points.nearestEuclidean(key, eps));
    }

	/**
     * Merges two lists together.
     *
     * @param one first list
     * @param two second list
     * @return merged lists
     */
    private List<Record<T>> merge(final List<Record<T>> one, final List<Record<T>> two) {
        final Set<Record<T>> oneSet = new HashSet<>(one);
        for (Record<T> item : two) {
            if (!oneSet.contains(item)) {
                one.add(item);
            }
        }
        return one;
    }

	public DBScan eps(double eps) {
		this.eps = eps;
        return this;
	}
	
	public DBScan minPoints(int minPoints) {
		this.minPoints = minPoints;
        return this;
	}
}
