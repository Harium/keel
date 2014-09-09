package test.br.com.etyllica.motion.ogr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;
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
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node leftNode = result.getNodes().get(0);
		Node rightNode = result.getNodes().get(1);
		
		Node lastRightNode = result.getNodes().get(2);
		Node lastLeftNode = result.getNodes().get(3);
		
		Assert.assertEquals(leftNode.getPoint().getX(), lastLeftNode.getPoint().getX(), 0);
		Assert.assertEquals(leftNode.getPoint().getY(), lastLeftNode.getPoint().getY(), 0);
		
		Assert.assertEquals(rightNode.getPoint().getX(), lastRightNode.getPoint().getX(), 0);
		Assert.assertEquals(rightNode.getPoint().getY(), lastRightNode.getPoint().getY(), 0);
		
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
		
		Node root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getPoint().getY(), 0);
		Assert.assertEquals(5, root.getPoint().getX(), 0);
		
		Node leftNode = result.getNodes().get(0);
		Assert.assertEquals(1, leftNode.getPoint().getY(), 0);
		Assert.assertEquals(0, leftNode.getPoint().getX(), 0);
		
		Node rightNode = result.getNodes().get(2);
		Assert.assertEquals(1, rightNode.getPoint().getY(), 0);
		Assert.assertEquals(9, rightNode.getPoint().getX(), 0);
		
	}
	
	@Test
	public void expandGraphTest() {
		
		final boolean[][] matrix = new boolean[5][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[2] = new boolean[] { true,  true,  true, false, false, false, false,  true,  true,  true};
		matrix[3] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[4] = new boolean[] {false, false,  true, false, false, false, false,  true, false, false};
				
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(3, result.getNodes().size());
		
		Node root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getPoint().getY(), 0);
		Assert.assertEquals(5, root.getPoint().getX(), 0);
		
		Node leftNode = result.getNodes().get(0);
		Assert.assertEquals(2, leftNode.getPoint().getY(), 0);
		Assert.assertEquals(0, leftNode.getPoint().getX(), 0);
		
		Node rightNode = result.getNodes().get(2);
		Assert.assertEquals(2, rightNode.getPoint().getY(), 0);
		Assert.assertEquals(9, rightNode.getPoint().getX(), 0);
		
	}
	
	@Test
	public void closeCornerGraphTest() {
		
		final boolean[][] matrix = new boolean[5][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[2] = new boolean[] { true,  true,  true, false, false, false, false,  true,  true,  true};
		matrix[3] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[4] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
				
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getPoint().getY(), 0);
		Assert.assertEquals(5, root.getPoint().getX(), 0);
				
	}
	
	@Test
	public void expandClosedCornerGraphTest() {
		
		final boolean[][] matrix = new boolean[5][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] {false, false, false, false,  true,  true, false, false, false, false};
		matrix[2] = new boolean[] { true,  true, false, false, false, false, false, false,  true,  true};
		matrix[3] = new boolean[] {false, false, false, false,  true,  true, false, false, false, false};
		matrix[4] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
				
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getPoint().getY(), 0);
		Assert.assertEquals(5, root.getPoint().getX(), 0);
		
		Node lastNode = result.getNodes().get(3);
		Assert.assertEquals(4, lastNode.getPoint().getY(), 0);
		Assert.assertEquals(5, lastNode.getPoint().getX(), 0);
				
	}
	
	@Test
	public void closeQuadGraphTest() {
		
		final boolean[][] matrix = new boolean[4][10];
		
		matrix[0] = new boolean[] {false, false,  true,  true,  true,  true,  true,  true, false, false};
		matrix[1] = new boolean[] {false, false,  true, false, false, false, false,  true, false, false};
		matrix[2] = new boolean[] {false,  true, false, false, false, false, false, false,  true, false};
		matrix[3] = new boolean[] { true,  true,  true,  true,  true,  true,  true,  true,  true,  true};
				
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node leftNode = result.getNodes().get(0);
		Assert.assertEquals(0, leftNode.getPoint().getY(), 0);
		Assert.assertEquals(2, leftNode.getPoint().getX(), 0);
		
		Node rightNode = result.getNodes().get(1);
		Assert.assertEquals(0, rightNode.getPoint().getY(), 0);
		Assert.assertEquals(7, rightNode.getPoint().getX(), 0);
		
	}
	
	@Test
	public void expandClosedQuadGraphTest() {
		
		final boolean[][] matrix = new boolean[4][10];
		
		matrix[0] = new boolean[] {false, false,  true,  true,  true,  true,  true,  true, false, false};
		matrix[1] = new boolean[] {false, false,  true, false, false, false, false,  true, false, false};
		matrix[2] = new boolean[] {false,  true,  true,  true,  true,  true,  true,  true,  true, false};
		matrix[3] = new boolean[] { true,  true,  true,  true,  true,  true,  true,  true,  true,  true};
				
		Graph result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node leftNode = result.getNodes().get(0);
		Assert.assertEquals(0, leftNode.getPoint().getY(), 0);
		Assert.assertEquals(2, leftNode.getPoint().getX(), 0);
		
		Node rightNode = result.getNodes().get(1);
		Assert.assertEquals(0, rightNode.getPoint().getY(), 0);
		Assert.assertEquals(7, rightNode.getPoint().getX(), 0);
		
		Node lastRightNode = result.getNodes().get(2);
		Node lastLeftNode = result.getNodes().get(3);
		
		Assert.assertEquals(0, lastLeftNode.getPoint().getX(), 0);
		Assert.assertEquals(3, lastLeftNode.getPoint().getY(), 0);
		
		Assert.assertEquals(9, lastRightNode.getPoint().getX(), 0);
		Assert.assertEquals(3, lastRightNode.getPoint().getY(), 0);
		
	}
	
}
