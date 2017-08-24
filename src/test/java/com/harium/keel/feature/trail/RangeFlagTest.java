package com.harium.keel.feature.trail;

import org.junit.Assert;
import org.junit.Test;

public class RangeFlagTest {

	@Test
	public void testOpposite() {
		Assert.assertTrue(RangeFlag.POSITIVE.isOpposite(RangeFlag.NEGATIVE));
		Assert.assertTrue(RangeFlag.NEGATIVE.isOpposite(RangeFlag.POSITIVE));
		Assert.assertFalse(RangeFlag.NEGATIVE.isOpposite(RangeFlag.NEGATIVE));
		Assert.assertFalse(RangeFlag.NEGATIVE.isOpposite(RangeFlag.NEUTRAL));
		Assert.assertFalse(RangeFlag.POSITIVE.isOpposite(RangeFlag.POSITIVE));
		Assert.assertFalse(RangeFlag.POSITIVE.isOpposite(RangeFlag.NEUTRAL));
		Assert.assertFalse(RangeFlag.NEUTRAL.isOpposite(RangeFlag.NEUTRAL));
	}
}
