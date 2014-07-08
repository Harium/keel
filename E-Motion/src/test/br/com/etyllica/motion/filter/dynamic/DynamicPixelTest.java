package test.br.com.etyllica.motion.filter.dynamic;

import org.junit.Assert;
import org.junit.Test;


import br.com.etyllica.motion.filter.dynamic.DynamicPixel;

public class DynamicPixelTest {

	@Test
	public void testIsValid() {
		
		Assert.assertFalse(DynamicPixel.isValid(DynamicPixel.UNKNOWN));
		
		Assert.assertFalse(DynamicPixel.isValid(DynamicPixel.UNKNOWN_TOUCHED));
		
		Assert.assertFalse(DynamicPixel.isValid(DynamicPixel.INVALID));
		
		Assert.assertFalse(DynamicPixel.isValid(DynamicPixel.INVALID_TOUCHED));
		
		Assert.assertTrue(DynamicPixel.isValid(DynamicPixel.VALID));
		
		Assert.assertTrue(DynamicPixel.isValid(DynamicPixel.VALID_TOUCHED));
		
	}
	
	@Test
	public void testIsTouched() {
		
		Assert.assertFalse(DynamicPixel.isTouched(DynamicPixel.UNKNOWN));
		
		Assert.assertTrue(DynamicPixel.isTouched(DynamicPixel.UNKNOWN_TOUCHED));
		
		Assert.assertFalse(DynamicPixel.isTouched(DynamicPixel.INVALID));
		
		Assert.assertTrue(DynamicPixel.isTouched(DynamicPixel.INVALID_TOUCHED));
		
		Assert.assertFalse(DynamicPixel.isTouched(DynamicPixel.VALID));
		
		Assert.assertTrue(DynamicPixel.isTouched(DynamicPixel.VALID_TOUCHED));
		
	}
	
	@Test
	public void testIsUnknown() {
		
		Assert.assertTrue(DynamicPixel.isUnknown(DynamicPixel.UNKNOWN));
		
		Assert.assertTrue(DynamicPixel.isUnknown(DynamicPixel.UNKNOWN_TOUCHED));
		
		Assert.assertFalse(DynamicPixel.isUnknown(DynamicPixel.INVALID));
		
		Assert.assertFalse(DynamicPixel.isUnknown(DynamicPixel.INVALID_TOUCHED));
		
		Assert.assertFalse(DynamicPixel.isUnknown(DynamicPixel.VALID));
		
		Assert.assertFalse(DynamicPixel.isUnknown(DynamicPixel.VALID_TOUCHED));
		
	}
	
	@Test
	public void testSetTouched() {
		
		Assert.assertEquals(DynamicPixel.UNKNOWN_TOUCHED, DynamicPixel.setTouched(DynamicPixel.UNKNOWN));
		
		Assert.assertEquals(DynamicPixel.UNKNOWN_TOUCHED, DynamicPixel.setTouched(DynamicPixel.UNKNOWN_TOUCHED));
		
		Assert.assertEquals(DynamicPixel.VALID_TOUCHED, DynamicPixel.setTouched(DynamicPixel.VALID));
		
		Assert.assertEquals(DynamicPixel.VALID_TOUCHED, DynamicPixel.setTouched(DynamicPixel.VALID_TOUCHED));
		
		Assert.assertEquals(DynamicPixel.INVALID_TOUCHED, DynamicPixel.setTouched(DynamicPixel.INVALID));
		
		Assert.assertEquals(DynamicPixel.INVALID_TOUCHED, DynamicPixel.setTouched(DynamicPixel.INVALID_TOUCHED));		
		
	}
	
	@Test
	public void testSetValid() {
		
		Assert.assertEquals(DynamicPixel.VALID, DynamicPixel.setValid(DynamicPixel.UNKNOWN));
		
		Assert.assertEquals(DynamicPixel.VALID_TOUCHED, DynamicPixel.setValid(DynamicPixel.UNKNOWN_TOUCHED));
		
		Assert.assertEquals(DynamicPixel.VALID, DynamicPixel.setValid(DynamicPixel.VALID));
		
		Assert.assertEquals(DynamicPixel.VALID_TOUCHED, DynamicPixel.setValid(DynamicPixel.VALID_TOUCHED));
		
		Assert.assertEquals(DynamicPixel.VALID, DynamicPixel.setValid(DynamicPixel.INVALID));
		
		Assert.assertEquals(DynamicPixel.VALID_TOUCHED, DynamicPixel.setValid(DynamicPixel.INVALID_TOUCHED));
		
	}
	
	@Test
	public void testSetInvalid() {
		
		Assert.assertEquals(DynamicPixel.INVALID, DynamicPixel.setInvalid(DynamicPixel.UNKNOWN));
		
		Assert.assertEquals(DynamicPixel.INVALID_TOUCHED, DynamicPixel.setInvalid(DynamicPixel.UNKNOWN_TOUCHED));
		
		Assert.assertEquals(DynamicPixel.INVALID, DynamicPixel.setInvalid(DynamicPixel.VALID));
		
		Assert.assertEquals(DynamicPixel.INVALID_TOUCHED, DynamicPixel.setInvalid(DynamicPixel.VALID_TOUCHED));
		
		Assert.assertEquals(DynamicPixel.INVALID, DynamicPixel.setInvalid(DynamicPixel.INVALID));
		
		Assert.assertEquals(DynamicPixel.INVALID_TOUCHED, DynamicPixel.setInvalid(DynamicPixel.INVALID_TOUCHED));
		
	}
	
}
