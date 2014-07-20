package test.br.com.etyllica.motion.filter.dynamic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.core.dynamic.DynamicMapMask;

public class DynamicMapMaskTest {

	private DynamicMapMask mask;
	
	@Before
	public void setUp() {
		
		mask = new DynamicMapMask(250, 20);
	}
	
	@Test
	public void testIsValid() {
		
		mask.setValid(20, 21);
		
		Assert.assertTrue(mask.isValid(20, 21));
	}
	
	@Test
	public void testIsInvalid() {
		
		mask.setInvalid(20, 21);
		
		Assert.assertTrue(mask.isInvalid(20, 21));
	
	}
	
	@Test
	public void testIsTouchedMap() {
		
		mask.setValid(200, 21);
		mask.setTouched(200, 21);
		
		Assert.assertTrue(mask.isTouched(200, 21));
	
	}
	
	
}
