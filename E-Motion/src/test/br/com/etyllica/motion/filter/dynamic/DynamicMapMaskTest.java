package test.br.com.etyllica.motion.filter.dynamic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.core.dynamic.DynamicMapMask;

public class DynamicMapMaskTest {

	private DynamicMapMask mask;
	
	@Before
	public void setUp() {
		
		mask = new DynamicMapMask();
		
	}
	
	@Test
	public void testIsKnown() {
		
		mask.setValid(20, 21);
		
		Assert.assertTrue(mask.isTouched(20, 21));
	}
	
	
}
