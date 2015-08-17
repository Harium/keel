package br.com.etyllica.motion.modifier.ogr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.linear.graph.Graph;

public class LetterOGRModifierTest {
	
	private LetterOGRModifier modifier;
	
	@Before
	public void setUp() {
		modifier = new LetterOGRModifier();
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
	}
	
}
