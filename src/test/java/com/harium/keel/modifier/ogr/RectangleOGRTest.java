package com.harium.keel.modifier.ogr;

import com.harium.keel.modifier.ogr.model.OGRNodeData;
import com.harium.storage.graph.Graph;
import com.harium.storage.graph.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node<OGRNodeData> leftNode = result.getNodes().get(0);
		Node<OGRNodeData> rightNode = result.getNodes().get(1);
		
		Node<OGRNodeData> lastRightNode = result.getNodes().get(2);
		Node<OGRNodeData> lastLeftNode = result.getNodes().get(3);
		
		Assert.assertEquals(leftNode.getData().getX(), lastLeftNode.getData().getX(), 0);
		Assert.assertEquals(leftNode.getData().getY(), lastLeftNode.getData().getY(), 0);
		
		Assert.assertEquals(rightNode.getData().getX(), lastRightNode.getData().getX(), 0);
		Assert.assertEquals(rightNode.getData().getY(), lastRightNode.getData().getY(), 0);
		
	}
	
	@Test
	public void firstLineCornerTest() {
		
		final boolean[][] matrix = new boolean[1][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false, true, false, false, false, false};
		
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(1, result.getNodes().size());
	}
	
	@Test
	public void openGraphTest() {
		
		final boolean[][] matrix = new boolean[2][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] { true,  true,  true, false, false, false, false, true,  true,  true};
				
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(3, result.getNodes().size());
		
		Node<OGRNodeData> root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getData().getY(), 0);
		Assert.assertEquals(5, root.getData().getX(), 0);
		
		Node<OGRNodeData> leftNode = result.getNodes().get(0);
		Assert.assertEquals(1, leftNode.getData().getY(), 0);
		Assert.assertEquals(0, leftNode.getData().getX(), 0);
		
		Node<OGRNodeData> rightNode = result.getNodes().get(2);
		Assert.assertEquals(1, rightNode.getData().getY(), 0);
		Assert.assertEquals(9, rightNode.getData().getX(), 0);
		
	}
	
	@Test
	public void expandGraphTest() {
		
		final boolean[][] matrix = new boolean[5][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[2] = new boolean[] { true,  true,  true, false, false, false, false,  true,  true,  true};
		matrix[3] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[4] = new boolean[] {false, false,  true, false, false, false, false,  true, false, false};
				
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(3, result.getNodes().size());
		
		Node<OGRNodeData> root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getData().getY(), 0);
		Assert.assertEquals(5, root.getData().getX(), 0);
		
		Node<OGRNodeData> leftNode = result.getNodes().get(0);
		Assert.assertEquals(2, leftNode.getData().getY(), 0);
		Assert.assertEquals(0, leftNode.getData().getX(), 0);
		
		Node<OGRNodeData> rightNode = result.getNodes().get(2);
		Assert.assertEquals(2, rightNode.getData().getY(), 0);
		Assert.assertEquals(9, rightNode.getData().getX(), 0);
		
	}
	
	@Test
	public void closeCornerGraphTest() {
		
		final boolean[][] matrix = new boolean[5][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[2] = new boolean[] { true,  true,  true, false, false, false, false,  true,  true,  true};
		matrix[3] = new boolean[] {false,  true,  true, false, false, false, false,  true,  true, false};
		matrix[4] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
				
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node<OGRNodeData> root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getData().getY(), 0);
		Assert.assertEquals(5, root.getData().getX(), 0);
				
	}
	
	@Test
	public void expandClosedCornerGraphTest() {
		
		final boolean[][] matrix = new boolean[5][10];
		
		matrix[0] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
		matrix[1] = new boolean[] {false, false, false, false,  true,  true, false, false, false, false};
		matrix[2] = new boolean[] { true,  true, false, false, false, false, false, false,  true,  true};
		matrix[3] = new boolean[] {false, false, false, false,  true,  true, false, false, false, false};
		matrix[4] = new boolean[] {false, false, false, false, false,  true, false, false, false, false};
				
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node<OGRNodeData> root = result.getNodes().get(1);
		Assert.assertEquals(0, root.getData().getY(), 0);
		Assert.assertEquals(5, root.getData().getX(), 0);
		
		Node<OGRNodeData> lastNode = result.getNodes().get(3);
		Assert.assertEquals(4, lastNode.getData().getY(), 0);
		Assert.assertEquals(5, lastNode.getData().getX(), 0);
				
	}
	
	@Test
	public void closeQuadGraphTest() {
		
		final boolean[][] matrix = new boolean[4][10];
		
		matrix[0] = new boolean[] {false, false,  true,  true,  true,  true,  true,  true, false, false};
		matrix[1] = new boolean[] {false, false,  true, false, false, false, false,  true, false, false};
		matrix[2] = new boolean[] {false,  true, false, false, false, false, false, false,  true, false};
		matrix[3] = new boolean[] { true,  true,  true,  true,  true,  true,  true,  true,  true,  true};
				
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node<OGRNodeData> leftNode = result.getNodes().get(0);
		Assert.assertEquals(0, leftNode.getData().getY(), 0);
		Assert.assertEquals(2, leftNode.getData().getX(), 0);
		
		Node<OGRNodeData> rightNode = result.getNodes().get(1);
		Assert.assertEquals(0, rightNode.getData().getY(), 0);
		Assert.assertEquals(7, rightNode.getData().getX(), 0);
		
	}
	
	@Test
	public void expandClosedQuadGraphTest() {
		
		final boolean[][] matrix = new boolean[4][10];
		
		matrix[0] = new boolean[] {false, false,  true,  true,  true,  true,  true,  true, false, false};
		matrix[1] = new boolean[] {false, false,  true, false, false, false, false,  true, false, false};
		matrix[2] = new boolean[] {false,  true,  true,  true,  true,  true,  true,  true,  true, false};
		matrix[3] = new boolean[] { true,  true,  true,  true,  true,  true,  true,  true,  true,  true};
				
		Graph<OGRNodeData> result = ogr.findGraph(matrix);
		
		Assert.assertEquals(4, result.getNodes().size());
		
		Node<OGRNodeData> leftNode = result.getNodes().get(0);
		Assert.assertEquals(0, leftNode.getData().getY(), 0);
		Assert.assertEquals(2, leftNode.getData().getX(), 0);
		
		Node<OGRNodeData> rightNode = result.getNodes().get(1);
		Assert.assertEquals(0, rightNode.getData().getY(), 0);
		Assert.assertEquals(7, rightNode.getData().getX(), 0);
		
		Node<OGRNodeData> lastRightNode = result.getNodes().get(2);
		Node<OGRNodeData> lastLeftNode = result.getNodes().get(3);
		
		Assert.assertEquals(0, lastLeftNode.getData().getX(), 0);
		Assert.assertEquals(3, lastLeftNode.getData().getY(), 0);
		
		Assert.assertEquals(9, lastRightNode.getData().getX(), 0);
		Assert.assertEquals(3, lastRightNode.getData().getY(), 0);
	}
}
