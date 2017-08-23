package br.com.etyllica.keel.classifier.cluster;

import com.harium.etyl.linear.Point2D;
import com.harium.storage.kdtree.KDTree;
import com.harium.storage.kdtree.KeyDuplicateException;
import com.harium.storage.kdtree.KeySizeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Forked from: http://alvinalexander.com/java/jwarehouse/commons-math3-3.6.1/src/main/java/org/apache/commons/math3/ml/clustering/DBSCANClusterer.java.shtml
 *
 */
public class DBScan {

    /** Maximum radius of the neighborhood to be considered. */
    private double eps;

    /** Minimum number of points needed for a cluster. */
    private int minPoints;

    /** Status of a point during the clustering process. */
    private enum PointStatus {
        /** The point has is considered to be noise. */
        NOISE,
        /** The point is already part of a cluster. */
        PART_OF_CLUSTER
    }

    /**
     * Creates a new instance of a DBSCANClusterer.
     *
     * @param eps maximum radius of the neighborhood to be considered
     * @param minPts minimum number of points needed for a cluster
     * @param measure the distance measure to use
     * @throws NotPositiveException if {@code eps < 0.0} or {@code minPts < 0}
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
     * @param points the points to cluster
     * @return the list of clusters
     * @throws NullArgumentException if the data points are null
     */
    public List<Cluster> cluster(final Collection<Point2D> points) {
    	final List<Cluster> clusters = new ArrayList<Cluster>();
        final Map<Point2D, PointStatus> visited = new HashMap<Point2D, DBScan.PointStatus>();

        KDTree<Point2D> tree = new KDTree<Point2D>(2);
        
        //Populate the kdTree
        for (final Point2D point : points) {
        	double[] key = {point.getX(), point.getY()};
        	try {
				tree.insert(key, point);
			} catch (KeySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
                
        for (final Point2D point : points) {
            if (visited.get(point) != null) {
                continue;
            }
            final List<Point2D> neighbors = getNeighbors(point, tree);
            if (neighbors.size() >= minPoints) {
                // DBSCAN does not care about center points
                final Cluster cluster = new Cluster(clusters.size());
                clusters.add(expandCluster(cluster, point, neighbors, tree, visited));
            } else {
                visited.put(point, PointStatus.NOISE);
            }
        }

        for (Cluster cluster : clusters) {
        	cluster.calculateCentroid();
        }
        
        return clusters;
    }

    /**
     * Expands the cluster to include density-reachable items.
     *
     * @param cluster Cluster to expand
     * @param point Point to add to cluster
     * @param neighbors List of neighbors
     * @param points the data set
     * @param visited the set of already visited points
     * @return the expanded cluster
     */
    private Cluster expandCluster(final Cluster cluster,
                                     final Point2D point,
                                     final List<Point2D> neighbors,
                                     final KDTree<Point2D> points,
                                     final Map<Point2D, PointStatus> visited) {
        cluster.addPoint(point);
        visited.put(point, PointStatus.PART_OF_CLUSTER);

        List<Point2D> seeds = new ArrayList<Point2D>(neighbors);
        int index = 0;
        while (index < seeds.size()) {
            Point2D current = seeds.get(index);
            PointStatus pStatus = visited.get(current);
            // only check non-visited points
            if (pStatus == null) {
                final List<Point2D> currentNeighbors = getNeighbors(current, points);
                if (currentNeighbors.size() >= minPoints) {
                    seeds = merge(seeds, currentNeighbors);
                }
            }

            if (pStatus != PointStatus.PART_OF_CLUSTER) {
                visited.put(current, PointStatus.PART_OF_CLUSTER);
                cluster.addPoint(current);
            }

            index++;
        }
        return cluster;
    }

    /**
     * Returns a list of density-reachable neighbors of a {@code point}.
     *
     * @param point the point to look for
     * @param points possible neighbors
     * @return the List of neighbors
     */
    private List<Point2D> getNeighbors(final Point2D point, KDTree<Point2D> points) {
    	double[] key = {point.getX(), point.getY()};
        final List<Point2D> neighbors = new ArrayList<Point2D>();
        
        try {
			neighbors.addAll(points.nearestEuclidean(key, eps));
		} catch (KeySizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return neighbors; 
    }

	/**
     * Merges two lists together.
     *
     * @param one first list
     * @param two second list
     * @return merged lists
     */
    private List<Point2D> merge(final List<Point2D> one, final List<Point2D> two) {
        final Set<Point2D> oneSet = new HashSet<Point2D>(one);
        for (Point2D item : two) {
            if (!oneSet.contains(item)) {
                one.add(item);
            }
        }
        return one;
    }

	public void setEps(double eps) {
		this.eps = eps;
	}
	
	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}
}
