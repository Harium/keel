package test.br.com.etyllica.motion.ogr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.motion.ogr.RectangularOGR;

public class RectangleOGRTest {
	
	private RectangularOGR ogr;
	
	@Before
	public void setUp() {
		ogr = new RectangularOGR();
	}
	
	@Test
	public void firstLineLongTest() {
		
		final boolean[][] matrix = new boolean[1][10];
		
		matrix[0] = new boolean[] {false, false, false, true, true, true, true, false, false, false};
		
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(2, result.getNodes().size());
		
	}
	
	@Test
	public void firstLineCornerTest() {
		
		final boolean[][] matrix = new boolean[1][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false, true, false, false, false, false};
		
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(1, result.getNodes().size());
		
	}
	
}
