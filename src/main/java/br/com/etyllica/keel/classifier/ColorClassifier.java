package br.com.etyllica.keel.classifier;

import com.harium.storage.kdtree.KDTree;
import com.harium.storage.kdtree.KeyDuplicateException;
import com.harium.storage.kdtree.KeySizeException;

public class ColorClassifier {

    private static final KDTree<String> COLOR_NAMES = init();

    private static KDTree<String> init() {

        KDTree<String> colorTree = new KDTree<String>(3);

        try {
            colorTree.insert(new double[]{0, 0, 0}, "black");
            colorTree.insert(new double[]{0xff, 0, 0}, "red");
            colorTree.insert(new double[]{0, 0xff, 0}, "green");
            colorTree.insert(new double[]{0, 0, 0xff}, "blue");
            colorTree.insert(new double[]{0xff, 0xff, 0}, "yellow");
            colorTree.insert(new double[]{0, 0xff, 0xff}, "cyan");
            colorTree.insert(new double[]{0xff, 0, 0xff}, "pink");
            colorTree.insert(new double[]{0xff, 0xff, 0xff}, "white");
        } catch (KeySizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyDuplicateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return colorTree;
    }

    public static String getColorName(int r, int g, int b) {
        double[] key = new double[]{r, g, b};

        try {
            return COLOR_NAMES.nearest(key);
        } catch (KeySizeException e) {
            return null;
        }
    }

}
