package org.opencv.criteria;

import java.util.EnumSet;

public class CvTermCriteria {

    private double epsilon = 0;

    private int maxIter = 1;

    private EnumSet<CriteriaType> type;

    public CvTermCriteria(double epsilon) {
        super();

        this.epsilon = epsilon;

        this.type = EnumSet.of(CriteriaType.CV_TERMCRIT_EPS);

    }

    public CvTermCriteria(int maxIter) {
        super();

        this.maxIter = maxIter;

        this.type = EnumSet.of(CriteriaType.CV_TERMCRIT_ITER);

    }

    public boolean hasType(CriteriaType type) {
        return this.type.contains(type);
    }

    public EnumSet<CriteriaType> getType() {
        return type;
    }

    public void setType(EnumSet<CriteriaType> type) {
        this.type = type;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

}
