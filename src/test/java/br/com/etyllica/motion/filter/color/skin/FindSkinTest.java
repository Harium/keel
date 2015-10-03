package br.com.etyllica.motion.filter.color.skin;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.filter.color.ColorStrategy;

public class FindSkinTest {

	private List<Integer> color;
	
	@Before
	public void setUp() {
		color = new ArrayList<Integer>();
	}
	
	@Test
	public void testFindDarkerSkin() {
		color.add(ColorStrategy.getRGB(96, 92, 81));
		color.add(ColorStrategy.getRGB(129, 130, 122));
		color.add(ColorStrategy.getRGB(129, 130, 122));
		color.add(ColorStrategy.getRGB(117, 119, 108));
		color.add(ColorStrategy.getRGB(102, 95, 77));
		color.add(ColorStrategy.getRGB(74, 71, 64));
		color.add(ColorStrategy.getRGB(66, 65, 60));
		
		int count = 0;
		for(Integer i:color) {
			if(testColor(i)) {
				count++;
			}
		}
		
		Assert.assertEquals(color.size(), count);
	}
	
	private boolean testColor(int rgb) {
		return SkinColorKovacNewStrategy.isSkin(rgb);
	}
	
}
