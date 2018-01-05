package com.harium.keel.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.filter.search.TriangularSearch;

public class BooleanMaskSearchTest {

	private BooleanMaskSearch filter;

	@Before
	public void setUp(){

		filter = new TriangularSearch(256, 256);

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
