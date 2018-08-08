package com.harium.keel.classifier;

import com.harium.storage.kdtree.KDTree;

public class ColorClassifier {

    private static final KDTree<String> COLOR_NAMES = init();

    private static KDTree<String> init() {

        KDTree<String> colorTree = new KDTree<String>(3);

        colorTree.insert(new double[]{0, 0, 0}, "black");
        colorTree.insert(new double[]{0xff, 0, 0}, "red");
        colorTree.insert(new double[]{0, 0xff, 0}, "green");
        colorTree.insert(new double[]{0, 0, 0xff}, "blue");
        colorTree.insert(new double[]{0xff, 0xff, 0}, "yellow");
        colorTree.insert(new double[]{0, 0xff, 0xff}, "cyan");
        colorTree.insert(new double[]{0xff, 0, 0xff}, "pink");
        colorTree.insert(new double[]{0xff, 0xff, 0xff}, "white");

        return colorTree;
    }

    public static String getColorName(int r, int g, int b) {
        double[] key = new double[]{r, g, b};
        return COLOR_NAMES.nearest(key);
    }

}
