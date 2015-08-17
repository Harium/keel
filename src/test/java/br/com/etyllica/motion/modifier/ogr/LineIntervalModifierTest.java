package br.com.etyllica.motion.modifier.ogr;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.feature.ogr.LineInterval;

public class LineIntervalModifierTest {
	
	private LineIntervalModifier modifier;
	
	@Before
	public void setUp() {
		modifier = new LineIntervalModifier();
	}
	
	@Test
	public void firstLineLongTest() {
		
		final boolean[][] matrix = new boolean[1][10];
		
		matrix[0] = new boolean[] {false, false, false, true, true, true, true, false, false, false};
	
		List<LineInterval> intervals = modifier.modify(matrix);
				
		Assert.assertEquals(1, intervals.size());
		Assert.assertEquals(4, intervals.get(0).getLength());
	}
	
	@Test
	public void twoIntervalsTest() {
		
		final boolean[][] matrix = new boolean[1][10];
		
		matrix[0] = new boolean[] {false, true, false, true, true, true, true, false, false, false};
	
		List<LineInterval> intervals = modifier.modify(matrix);
				
		Assert.assertEquals(2, intervals.size());
		Assert.assertEquals(1, intervals.get(0).getLength());
		Assert.assertEquals(4, intervals.get(1).getLength());
	}
	
	@Test
	public void doubleIntervalsTest() {
		
		final boolean[][] matrix = new boolean[2][10];
		
		matrix[0] = new boolean[] {false, true, false, true, true, true, true, false, false, false};
		matrix[1] = new boolean[] {false, true, false, true, true, true, true, false, false, false};
	
		List<LineInterval> intervals = modifier.modify(matrix);
				
		Assert.assertEquals(4, intervals.size());
		Assert.assertEquals(1, intervals.get(0).getLength());
		Assert.assertEquals(4, intervals.get(1).getLength());
		Assert.assertEquals(1, intervals.get(2).getLength());
		Assert.assertEquals(4, intervals.get(3).getLength());
	}

}
