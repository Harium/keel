package com.harium.keel.effect.watershed;

import java.util.Vector;

public class SortedVector {
    Vector<Comparable<Object>> data;

    public SortedVector() {
        data = new Vector<Comparable<Object>>();
    }

    public void add(Object o) {
        if (o instanceof Comparable) {
            insert((Comparable<Object>) o);
        } else {
            throw new IllegalArgumentException("Object " +
                    "must implement Comparable interface.");
        }
    }

    private void insert(Comparable<Object> o) {
        if (data.size() == 0) {
            data.add(o);
        }
        int middle = 0;
        int left = 0;
        int right = data.size() - 1;
        while (left < right) {
            middle = (left + right) / 2;
            if (data.elementAt(middle).compareTo(o) == -1) {
                left = middle + 1;
            } else if (data.elementAt(middle).compareTo(o) == 1) {
                right = middle - 1;
            } else {
                // position found, insert here
                // break out while
                left = data.size() + 1;
            }
        }
        data.add(middle, o);
    }

    public int size() {
        return data.size();
    }

    public Object elementAt(int index) {
        return data.elementAt(index);
    }

    public Object remove(int position) {
        return data.remove(position);
    }
}
