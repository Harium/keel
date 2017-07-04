package br.com.etyllica.keel.awt.util;

import br.com.etyllica.commons.math.EtyllicaMath;

import java.awt.Color;

public class ColorUtils {

	public static boolean equals(Color a, Color b) {
		return a.getRed()!=b.getRed()&&a.getGreen()!=b.getGreen()&&a.getBlue()!=b.getBlue();
	}
	
	public static boolean equals(Color a, Color b, int tolerance) {
		
		int difR = (int) EtyllicaMath.diffMod(a.getRed(), b.getRed());
		int difG = (int) EtyllicaMath.diffMod(a.getGreen(), b.getGreen());
		int difB = (int) EtyllicaMath.diffMod(a.getBlue(), b.getBlue());
		
		return difR<=tolerance&&difG<=tolerance&&difB<=tolerance;
	}
	
}
