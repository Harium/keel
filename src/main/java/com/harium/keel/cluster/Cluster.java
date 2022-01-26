package com.harium.keel.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cluster<T> {

    public static final int UNDEFINED_CLUSTER = -1;

    public Centroid<T> centroid;
    private List<Record<T>> records;

    public Cluster() {
        this.records = new ArrayList<>();
        this.centroid = null;
    }

    public void addRecord(Record<T> record) {
        if (records == null) {
           records = new ArrayList<>();
        }
        records.add(record);
    }

    public List<Record<T>> getRecords() {
        return records;
    }

    public void setRecords(List<Record<T>> records) {
        this.records = records;
    }

    public Centroid<T> getCentroid() {
        return centroid;
    }

    public void setCentroid(Centroid<T> centroid) {
        this.centroid = centroid;
    }

    public Centroid<T> calculateCentroid() {
        int len = records.get(0).getFeatures().length;
        double[] avg = new double[len];
        Arrays.fill(avg, 0);

        for (Record<T> record : records) {
            double[] point = record.getFeatures();
            for (int i = 0; i < avg.length; i++) {
                avg[i] += point[i];
            }
        }
        for (int i = 0; i < avg.length; i++) {
            avg[i] /= records.size();
        }

        centroid = new Centroid<>(avg);
        return centroid;
    }

    public void clear() {
        records.clear();
    }

}
