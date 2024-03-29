package com.harium.keel.cluster;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KMeansTest {

    KMeans<String> clusterFit;

    @Before
    public void setUp() {
        clusterFit = new KMeans<>();
    }

    @Test
    public void testFitSingleGroup() {
        int k = 1;
        clusterFit.k(1).maxIterations(100);

        List<Record<String>> data = getDataset();

        List<Cluster<String>> result = clusterFit.fit(data);
        assertEquals(k, result.size());
    }

    private List<Record<String>> getDataset() {
        Record<String> row1 = new Record<>("row1", new double[]{0, 1, 2});
        Record<String> row2 = new Record<>("row2", new double[]{6, 1, 2});
        Record<String> row3 = new Record<>("row3", new double[]{2, 4, 2});
        List<Record<String>> data = Arrays.asList(row1, row2, row3);
        return data;
    }

}
