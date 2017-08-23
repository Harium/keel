package br.com.etyllica.keel.modifier.ogr;

import br.com.etyllica.keel.feature.ogr.LineInterval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

	@Test
	public void letterATest() {
		final boolean[][] matrix = new boolean[5][5];

		matrix[0] = new boolean[] {false, false,  true, false, false};
		matrix[1] = new boolean[] {false,  true, false,  true, false};
		matrix[2] = new boolean[] { true,  true,  true,  true,  true};
		matrix[3] = new boolean[] { true, false, false, false,  true};
		matrix[4] = new boolean[] { true, false, false, false, true};

		List<LineInterval> intervals = modifier.modify(matrix);

		Assert.assertEquals(8, intervals.size());
	}

}
