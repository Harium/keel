package br.com.etyllica.keel.classifier;

import org.junit.Assert;
import org.junit.Test;

public class PolygonClassifierTest {

	@Test
	public void testTriangle() {
		Assert.assertEquals("triangle", PolygonClassifier.indentifyRegionByNumberOfPoints(3));
	}
	
	@Test
	public void testRectangle() {
		Assert.assertEquals("rectangle", PolygonClassifier.indentifyRegionByNumberOfPoints(4));
	}
	
}
