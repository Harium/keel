package com.harium.keel.core.mask;

import com.harium.keel.core.source.ImageSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicMapMask implements DynamicMask {

    private int w;

    private int h;

    private Map<Integer, Set<Integer>> isInvalid = new HashMap<Integer, Set<Integer>>();

    private Map<Integer, Set<Integer>> isValid = new HashMap<Integer, Set<Integer>>();

    private Map<Integer, Set<Integer>> isTouchedMap = new HashMap<Integer, Set<Integer>>();

    private Set<Integer> isLineTouched = new HashSet<Integer>();

    public DynamicMapMask(int w, int h) {
        super();
        init(w, h);
    }

    @Override
    public void init(int w, int h) {
        this.w = w;
        this.h = h;

        isValid = new HashMap<Integer, Set<Integer>>();
        isInvalid = new HashMap<Integer, Set<Integer>>();
        isTouchedMap = new HashMap<Integer, Set<Integer>>();
        isLineTouched = new HashSet<Integer>();
    }

    @Override
    public boolean isUnknown(int px, int py) {

        boolean isValidPoint = isValid(px, py);

        boolean isInvalidPoint = isInvalid(px, py);

        return !isValidPoint && !isInvalidPoint;
    }

    @Override
    public boolean isTouched(int px, int py) {

        if (isLineTouched.contains(px)) {

            return true;

        } else {

            Set<Integer> ySet = isTouchedMap.get(px);

            if (ySet != null) {
                return ySet.contains(py);
            }

        }

        return false;
    }

    @Override
    public boolean isValid(int px, int py) {

        Set<Integer> ySet = isValid.get(px);

        if (ySet != null) {

            return ySet.contains(py);
        }

        return false;
    }

    public boolean isInvalid(int px, int py) {

        Set<Integer> ySet = isInvalid.get(px);

        if (ySet != null) {

            return ySet.contains(py);
        }

        return false;
    }

    @Override
    public void setTouched(int px, int py) {

        Set<Integer> ySet = isTouchedMap.get(px);

        if (ySet == null) {

            ySet = new HashSet<Integer>();

            isTouchedMap.put(px, ySet);
        }

        //If line is not complete
        if (ySet.size() != w - 1) {

            ySet.add(py);

        } else {

            isLineTouched.add(px);
            isTouchedMap.remove(px);
        }

    }

    @Override
    public void setValid(int px, int py) {

        Set<Integer> ySet = isValid.get(px);

        if (ySet == null) {

            ySet = new HashSet<Integer>();

            isValid.put(px, ySet);
        }

        ySet.add(py);

    }

    @Override
    public void setInvalid(int px, int py) {

        Set<Integer> ySet = isInvalid.get(px);

        if (ySet == null) {

            ySet = new HashSet<Integer>();

            isInvalid.put(px, ySet);
        }

        ySet.add(py);
    }

    @Override
    public void update(ImageSource source) {
        isValid.clear();
        isInvalid.clear();
        isTouchedMap.clear();
        isLineTouched.clear();
    }

    @Override
    public int getW() {
        return w;
    }

    @Override
    public int getH() {
        return h;
    }
}
