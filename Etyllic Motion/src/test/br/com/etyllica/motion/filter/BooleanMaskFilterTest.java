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

		filter = new ColorFilter(256, 256);

		filter.updateMask(0, 0, 256, 256, true);

	}

	@Test
	public void testUpdateMask(){

		boolean[][] mask = filter.getMask();

		int w = mask.length;
		
		int h = mask[0].length;

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				Assert.assertTrue(filter.getMask()[i][j]);	
			}
		}		

	}
}
