package test.br.com.etyllica.motion.ogr;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
import br.com.etyllica.motion.ogr.LineInterval;
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
	
	@Test
	public void openGraphTest() {
		
		final boolean[][] matrix = new boolean[2][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] { true,  true,  true, false, false, false, false, true,  true,  true};
				
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(3, result.getNodes().size());
		
		Node root = result.getNodes().get(0);
		Assert.assertEquals(0, root.getY(), 0);
		Assert.assertEquals(5, root.getX(), 0);
		
		Node leftNode = result.getNodes().get(1);
		Assert.assertEquals(1, leftNode.getY(), 0);
		Assert.assertEquals(1, leftNode.getX(), 0);
		
		Node rightNode = result.getNodes().get(2);
		Assert.assertEquals(1, rightNode.getY(), 0);
		Assert.assertEquals(8, rightNode.getX(), 0);
		
	}
	
}
