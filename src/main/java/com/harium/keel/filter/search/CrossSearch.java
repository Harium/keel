package com.harium.keel.filter.search;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.strategy.PixelStrategy;
import com.harium.keel.core.strategy.SearchFilter;
import com.harium.keel.feature.Component;
import com.harium.keel.feature.Cross;

public class CrossSearch extends SearchFilter {

    private Cross cross = new Cross();

    public CrossSearch() {
        super();
    }

    public CrossSearch(PixelStrategy pixelStrategy) {
        super();
        this.pixelStrategy = pixelStrategy;
    }

    public boolean filterFirst(int x, int y, int width, int height, ImageSource source) {
        return filter(x, y, width, height, source);
    }

    public boolean filter(int x, int y, int width, int height, ImageSource source) {
        Component holder;

        if (results.isEmpty()) {
            holder = new Component(width, height);
            results.add(holder);
        } else {
            holder = results.get(0);
        }

        if (pixelStrategy.validateColor(source.getRGB(x, y), x, y)) {

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
        return validateCross(cross, "FFF FTT TTT");
    }

    private boolean downHardCorner(Cross cross) {
        return validateCross(cross, "TTT TTF FFF");
    }

    private boolean leftHardCorner(Cross cross) {
        return validateCross(cross, "FFT FTT FFF");
    }

    private boolean rightHardCorner(Cross cross) {
        return validateCross(cross, "TFF TTF TTF");
    }

    public boolean validateCross(Cross cross, String pattern) {

        boolean result = getCrossString(cross).matches(pattern.replaceAll(" ", ""));

        return result;

    }

    public boolean validateCross(int j, int i, Cross cross, boolean upperLeft, boolean up, boolean upperRight, boolean left, boolean center, boolean right, boolean lowerLeft, boolean down, boolean lowerRight) {

        boolean result = pixelStrategy.validateColor(cross.getUpperLeft(), j - 1, i - 1) == upperLeft &&
                pixelStrategy.validateColor(cross.getUp(), j, i - 1) == up &&
                pixelStrategy.validateColor(cross.getUpperRight(), j + 1, i - 1) == upperRight &&
                pixelStrategy.validateColor(cross.getLeft(), j - 1, i) == left &&
                pixelStrategy.validateColor(cross.getCenter(), j, i) == center &&
                pixelStrategy.validateColor(cross.getRight(), j + 1, i) == right &&
                pixelStrategy.validateColor(cross.getLowerLeft(), j - 1, i + 1) == lowerLeft &&
                pixelStrategy.validateColor(cross.getDown(), j, i + 1) == down &&
                pixelStrategy.validateColor(cross.getLowerRight(), j + 1, i + 1) == lowerRight;

        return result;
    }

    public String getCrossString(Cross cross) {

        StringBuilder builder = new StringBuilder();

        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getUpperLeft(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getUp(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getUpperRight(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getLeft(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getCenter(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getRight(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getLowerLeft(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getDown(), 0, 0)));
        builder.append(booleanToChar(pixelStrategy.validateColor(cross.getLowerRight(), 0, 0)));

        return builder.toString();
    }

    private char booleanToChar(boolean b) {

        if (b) {
            return 'T';
        }

        return 'F';

    }

}
