package br.com.etyllica.keel.awt.util;

import com.harium.etyl.commons.math.EtylMath;

import java.awt.*;

public class ColorUtils {

    public static boolean equals(Color a, Color b) {
        return a.getRed() != b.getRed() && a.getGreen() != b.getGreen() && a.getBlue() != b.getBlue();
    }

    public static boolean equals(Color a, Color b, int tolerance) {

        int difR = (int) EtylMath.diffMod(a.getRed(), b.getRed());
        int difG = (int) EtylMath.diffMod(a.getGreen(), b.getGreen());
        int difB = (int) EtylMath.diffMod(a.getBlue(), b.getBlue());

        return difR <= tolerance && difG <= tolerance && difB <= tolerance;
    }

}
