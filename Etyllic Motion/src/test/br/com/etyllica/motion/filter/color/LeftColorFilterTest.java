package test.br.com.etyllica.motion.filter.color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.LeftColorFilter;

public class LeftColorFilterTest {

	private BufferedImage image;
	
	private LeftColorFilter filter;
	
	private final int IMAGE_WIDTH = 640;
	
	private final int IMAGE_HEIGHT = 480;
	
	private final int RGB = Color.YELLOW.getRGB();
	
	@Before
	public void setUp(){
		
		image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = image.createGraphics();
		
		g.setColor(Color.WHITE);
		
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
				
		filter = new LeftColorFilter();
				
		filter.setColor(RGB);
		
		filter.setBorder(1);
		
		filter.setTolerance(10);
		
	}
	
	@Test
	public void testFilterFirst(){
		
		image.setRGB(20, 30, RGB);
		
		Component point = filter.filterFirst(image, new BoundingComponent(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT));
		
		Assert.assertEquals(20, point.getX(), 0);
		
		Assert.assertEquals(30, point.getY(), 0);
		
	}
	
	@Test
	public void testFilterFirstWithBorder(){
		
		image.setRGB(2, 30, RGB);
		
		image.setRGB(20, 30, RGB);
		
		filter.setBorder(3);		
		
		Component point = filter.filterFirst(image, new BoundingComponent(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT));
		
		Assert.assertEquals(20, point.getX(), 0);
		
		Assert.assertEquals(30, point.getY(), 0);
		
	}
	
}
