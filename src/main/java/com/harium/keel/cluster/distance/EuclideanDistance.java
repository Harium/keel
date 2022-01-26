package com.harium.keel.cluster.distance;

public class EuclideanDistance implements DistanceMeasure {

	public double distance(double[] a, double[] b) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			double valueA = a[i];
			double valueB = b[i];

			sum += Math.pow(valueA - valueB, 2);
		}

		return Math.sqrt(sum);
	}

}
