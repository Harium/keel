package br.com.etyllica.motion.modifier.ogr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.graph.Graph;
import br.com.etyllica.linear.graph.Node;

public class LetterOGRModifierTest {
	
	private LetterOGRModifier modifier;
	
	@Before
	public void setUp() {
		modifier = new LetterOGRModifier();
	}
	
	@Test
	public void letterATest() {
		final boolean[][] matrix = new boolean[5][5];
		
		matrix[0] = new boolean[] {false, false,  true, false, false};
		matrix[1] = new boolean[] {false,  true, false,  true, false};
		matrix[2] = new boolean[] { true,  true,  true,  true,  true};
		matrix[3] = new boolean[] { true, false, false, false,  true};
		matrix[4] = new boolean[] { true, false, false, false, true};
	
		Graph<Integer> graph = modifier.modify(matrix);
		
		Assert.assertEquals(6, graph.getNodes().size());
		
		Node<Integer> root = graph.getNodes().get(0);
		Assert.assertEquals(2, root.getPoint().getX(), 0);
		Assert.assertEquals(0, root.getPoint().getY(), 0);
	}
	
	@Test
	public void letterBTest() {
		final boolean[][] matrix = new boolean[5][5];
		
		matrix[0] = new boolean[] { true,  true,  true, true, false};
		matrix[1] = new boolean[] { true, false, false, false, true};
		matrix[2] = new boolean[] { true,  true,  true,  true, true};
		matrix[3] = new boolean[] { true, false, false, false, true};
		matrix[4] = new boolean[] { true,  true,  true,  true, true};
	
		Graph<Integer> graph = modifier.modify(matrix);
		
		Assert.assertEquals(7, graph.getNodes().size());
				
		Node<Integer> root = graph.getNodes().get(0);
		Assert.assertEquals(2, root.getPoint().getX(), 0);//Center of first line
		Assert.assertEquals(0, root.getPoint().getY(), 0);
	}
	
	@Test
	public void letterCTest() {
		final boolean[][] matrix = new boolean[5][5];
		
		matrix[0] = new boolean[] {false, false,  true, false, false};
		matrix[1] = new boolean[] {false,  true, false,  true, false};
		matrix[2] = new boolean[] { true, false, false, false, false};
		matrix[3] = new boolean[] {false,  true, false,  true, false};
		matrix[4] = new boolean[] {false, false,  true, false, false};
	
		Graph<Integer> graph = modifier.modify(matrix);
		
		Assert.assertEquals(7, graph.getNodes().size());
				
		Node<Integer> root = graph.getNodes().get(0);
		Assert.assertEquals(2, root.getPoint().getX(), 0);//Center of first line
		Assert.assertEquals(0, root.getPoint().getY(), 0);
	}
	
	@Test
	public void letterKTest() {
		final boolean[][] matrix = new boolean[5][5];
		
		matrix[0] = new boolean[] {true, false, false, true, false};
		matrix[1] = new boolean[] {true, false,  true, false, false};
		matrix[2] = new boolean[] {true,  true, false, false, false};
		matrix[3] = new boolean[] {true, false,  true, false, false};
		matrix[4] = new boolean[] {true, false, false, true, false};
	
		Graph<Integer> graph = modifier.modify(matrix);
		
		Assert.assertEquals(5, graph.getNodes().size());
		
		Node<Integer> root = graph.getNodes().get(0);
		Assert.assertEquals(0, root.getPoint().getX(), 0);
		Assert.assertEquals(0, root.getPoint().getY(), 0);
	}
	
}
