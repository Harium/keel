package br.com.etyllica.keel.classifier;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.keel.classifier.ColorClassifier;

public class ColorClassifierTest {

	@Test
	public void testRed() {
		
		Color color = SVGColor.CRIMSON;
		
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		Assert.assertEquals("red", ColorClassifier.getColorName(r, g, b));
	}
	
	@Test
	public void testGreen() {
		
		Color color = SVGColor.LIME_GREEN;
		
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		Assert.assertEquals("green", ColorClassifier.getColorName(r, g, b));
	}
	
}
