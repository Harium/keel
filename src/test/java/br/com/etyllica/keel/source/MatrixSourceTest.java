package br.com.etyllica.keel.source;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.keel.core.source.MatrixSource;

public class MatrixSourceTest {

    private MatrixSource checkerSource;
    
    private final int SOURCE_WIDTH = 2;
    private final int SOURCE_HEIGHT = 2;
    
    @Before
    public void setUp() {
      checkerSource = new MatrixSource(SOURCE_WIDTH, SOURCE_HEIGHT);
      checkerSource.getMatrix()[0][0] = SVGColor.BLACK.getRGB();
      checkerSource.getMatrix()[1][0] = SVGColor.WHITE.getRGB();
      checkerSource.getMatrix()[0][1] = SVGColor.WHITE.getRGB();
      checkerSource.getMatrix()[1][1] = SVGColor.BLACK.getRGB();
    }
    
    @Test
    public void testGetRgb() {      
      Assert.assertEquals(SVGColor.BLACK.getRGB(), checkerSource.getRGB(0, 0));
      Assert.assertEquals(SVGColor.BLACK.getRGB(), checkerSource.getRGB(1, 1));
      Assert.assertEquals(SVGColor.WHITE.getRGB(), checkerSource.getRGB(0, 1));
      Assert.assertEquals(SVGColor.WHITE.getRGB(), checkerSource.getRGB(1, 0));
      
      Assert.assertNotEquals(SVGColor.BLUE.getRGB(), checkerSource.getRGB(1, 0));
    }
    
}
