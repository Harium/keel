package test.br.com.etyllica.motion.filter;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import br.com.etyllica.motion.features.Cross;
import br.com.etyllica.motion.filter.wand.BorderFilter;

public class BorderFilterTest {

	private BorderFilter filter;
	
	@Before
	public void setUp(){
		filter = new BorderFilter(200, 200);
		
		//Looking for Black Pixels
		filter.setColor(Color.BLACK);
	}
	
	@Test
	public void testCrossPattern(){
		
		Cross cross = new Cross();
		
		cross.setUpperLeft(Color.BLACK.getRGB());
		cross.setUp(Color.BLACK.getRGB());
		cross.setUpperRight(Color.BLACK.getRGB());
		
		cross.setLeft(Color.BLACK.getRGB());
		cross.setCenter(Color.WHITE.getRGB());
		cross.setRight(Color.WHITE.getRGB());
		
		cross.setLowerLeft(Color.WHITE.getRGB());
		cross.setDown(Color.WHITE.getRGB());
		cross.setLowerRight(Color.WHITE.getRGB());
		
		assertTrue(filter.validateCross(cross, "TTT TFF FFF"));
		
	}
	
	@Test
	public void testOptionalPattern(){
		
		Cross cross = new Cross();
		
		cross.setUpperLeft(Color.BLACK.getRGB());
		cross.setUp(Color.BLACK.getRGB());
		cross.setUpperRight(Color.BLACK.getRGB());
		
		cross.setLeft(Color.BLACK.getRGB());
		cross.setCenter(Color.WHITE.getRGB());
		cross.setRight(Color.WHITE.getRGB());
		
		cross.setLowerLeft(Color.WHITE.getRGB());
		cross.setDown(Color.WHITE.getRGB());
		cross.setLowerRight(Color.WHITE.getRGB());
		
		assertTrue(filter.validateCross(cross, "TTT TFF FF."));
		
	}
	
}
