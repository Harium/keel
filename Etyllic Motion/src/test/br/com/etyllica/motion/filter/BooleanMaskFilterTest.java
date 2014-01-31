package test.br.com.etyllica.motion.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.core.BooleanMaskFilter;
import br.com.etyllica.motion.filter.color.ColorFilter;

public class BooleanMaskFilterTest {

	private BooleanMaskFilter filter;
	
	@Before
	public void setUp(){
		
		filter = new ColorFilter(200, 200);
		
		filter.updateMask(0, 0, 200, 200, true);

	}
	
	@Test
	public void testUpdateMask(){
		
		Assert.assertTrue(filter.getMask()[0][0]);
		Assert.assertTrue(filter.getMask()[199][199]);
		
	}
}
