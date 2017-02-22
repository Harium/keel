package br.com.etyllica.motion.feature;

import org.junit.Assert;
import org.junit.Test;

public class IntervalTest {

	@Test
	public void testMerge() {
		Interval interval = new Interval(0, 2);
		Interval otherInterval = new Interval(1, 20);
		
		interval.merge(otherInterval);
		Assert.assertEquals(0, interval.getStart());
		Assert.assertEquals(20, interval.getEnd());
	}
	
}
