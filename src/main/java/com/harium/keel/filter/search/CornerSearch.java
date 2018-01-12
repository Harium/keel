package com.harium.keel.filter.search;

import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.feature.Cross;

public class CornerSearch extends BooleanMaskSearch {

    private Cross cross = new Cross();

    public CornerSearch(int w, int h) {
        super(w, h);
    }

    @Override
    public boolean filterFirst(int x, int y, int width, int height, ImageSource source, Feature component) {
        if (!mask[x][y] && selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
            setCross(x, y, source);

            if (isCorner(cross)) {
                lastComponent.add(x, y);
                mask[x][y] = true;
            }
        }

        return false;
    }

    @Override
    public boolean filter(int x, int y, int width, int height, ImageSource source, Feature component) {
        PointFeature holder;

        if (results.isEmpty()) {
            holder = new PointFeature(w, h);
            results.add(holder);
        } else {
            holder = results.get(0);
        }

        if (selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
            setCross(x, y, source);

            if (isCorner(cross)) {
                holder.add(x, y);
            }
        }

        return false;
    }

    private void setCross(int i, int j, ImageSource b) {
        cross.setUp(b.getRGB(i, j - step));
        cross.setDown(b.getRGB(i, j + step));
        cross.setLeft(b.getRGB(i - step, j));
        cross.setRight(b.getRGB(i + step, j));
        cross.setCenter(b.getRGB(i, j));

        cross.setLowerLeft(b.getRGB(i - step, j + step));
        cross.setUpperLeft(b.getRGB(i - step, j - step));
        cross.setLowerRight(b.getRGB(i + step, j + step));
        cross.setUpperRight(b.getRGB(i + step, j - step));
    }

    private boolean isCorner(Cross cross) {

        if (rightUpperCorner(cross)) {
            return true;
        } else if (leftUpperCorner(cross)) {
            return true;
        } else if (leftLowerCorner(cross)) {
            return true;
        } else if (rightLowerCorner(cross)) {
            return true;
        } else if (diagonalLeftCorner(cross)) {
            return true;
        } else if (diagonalRightCorner(cross)) {
            return true;
        } else if (diagonalUpCorner(cross)) {
            return true;
        } else if (diagonalDownCorner(cross)) {
            return true;
        }

        //Hard Alias
        else if (upHardCorner(cross)) {
            return true;
        } else if (downHardCorner(cross)) {
            return true;
        } else if (leftHardCorner(cross)) {
            return true;
        } else if (rightHardCorner(cross)) {
            return true;
        }

        return false;
    }

    private boolean leftUpperCorner(Cross cross) {
        boolean result = validateCross(cross, "FFF FTT FT(T|F)");

        return result;
    }

    private boolean rightUpperCorner(Cross cross) {
        boolean result = validateCross(cross, "FFF TTF (T|F)TF");

        return result;
    }

    private boolean leftLowerCorner(Cross cross) {

        boolean result = validateCross(cross, "FT(T|F) FTT FFF");

        return result;
    }

    private boolean rightLowerCorner(Cross cross) {
        boolean result = validateCross(cross, "(T|F)TF TTF FFF");
        return result;
    }

    private boolean diagonalUpCorner(Cross cross) {
        return validateCross(cross, "FFF FTF TTT");
    }

    private boolean diagonalLeftCorner(Cross cross) {
        return validateCross(cross, "FFT FTT FFT");
    }

    private boolean diagonalRightCorner(Cross cross) {
        return validateCross(cross, "TFF TTF TFF");
    }

    private boolean diagonalDownCorner(Cross cross) {
        return validateCross(cross, "TTT FTF FFF");
    }

    private boolean upHardCorner(Cross cross) {
        return validateCross(cross, "FFF FT(T|F) (T|F)T(T|F)");
    }

    private boolean downHardCorner(Cross cross) {
        return validateCross(cross, "(T|F)T(T|F) (T|F)TF FFF");
    }

    private boolean leftHardCorner(Cross cross) {
        return validateCross(cross, "F(T|F)(T|F) F(T|F)T FF(T|F)");
    }

    private boolean rightHardCorner(Cross cross) {
        return validateCross(cross, "(T|F)(T|F)F T(T|F)F (T|F)FF");
    }

    public boolean validateCross(Cross cross, String pattern) {

        boolean result = getCrossString(cross).matches(pattern.replaceAll(" ", ""));

        return result;

    }

    public boolean validateCross(int j, int i, Cross cross, boolean upperLeft, boolean up, boolean upperRight, boolean left, boolean center, boolean right, boolean lowerLeft, boolean down, boolean lowerRight) {

        boolean result = selectionStrategy.validateColor(cross.getUpperLeft(), j - 1, i - 1) == upperLeft &&
                selectionStrategy.validateColor(cross.getUp(), j, i - 1) == up &&
                selectionStrategy.validateColor(cross.getUpperRight(), j + 1, i - 1) == upperRight &&
                selectionStrategy.validateColor(cross.getLeft(), j - 1, i) == left &&
                selectionStrategy.validateColor(cross.getCenter(), j, i) == center &&
                selectionStrategy.validateColor(cross.getRight(), j + 1, i) == right &&
                selectionStrategy.validateColor(cross.getLowerLeft(), j - 1, i + 1) == lowerLeft &&
                selectionStrategy.validateColor(cross.getDown(), j, i + 1) == down &&
                selectionStrategy.validateColor(cross.getLowerRight(), j + 1, i + 1) == lowerRight;

        return result;
    }

    public String getCrossString(Cross cross) {

        StringBuilder builder = new StringBuilder();

        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getUpperLeft(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getUp(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getUpperRight(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getLeft(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getCenter(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getRight(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getLowerLeft(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getDown(), 0, 0)));
        builder.append(booleanToChar(selectionStrategy.validateColor(cross.getLowerRight(), 0, 0)));

        return builder.toString();
    }

    private char booleanToChar(boolean b) {

        if (b) {
            return 'T';
        }

        return 'F';

    }

}
