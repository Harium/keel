package com.harium.keel.feature;

public class Interval {

    private int start;
    private int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Interval(Interval interval) {
        this.start = interval.start;
        this.end = interval.end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int length() {
        return end - start;
    }

    public void merge(Interval interval) {
        start = Math.min(start, interval.getStart());
        end = Math.max(end, interval.getEnd());
    }
}
