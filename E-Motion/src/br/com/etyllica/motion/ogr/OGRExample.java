package br.com.etyllica.motion.ogr;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.ogr.LineInterval;

/**
 * Optical Graph Recognition Example
 */
public class OGRExample {

	public static void main(String[] args) {

		int[][] matrix = new int[4][10];

		matrix[0] = new int[]{0,0,1,1,1,0,0,1,1,0};
		matrix[1] = new int[]{0,0,1,0,1,0,0,0,0,0};
		matrix[2] = new int[]{0,1,1,0,1,0,0,0,0,0};
		matrix[3] = new int[]{0,1,0,0,0,1,0,0,0,0};

		List<LineInterval> intervals = new ArrayList<LineInterval>();

		int startInterval = 0;

		boolean foundInterval = false;

		for(int i=0; i<10; i++) {

			if(isValid(matrix[0][i])) {

				if(!foundInterval) {
					
					foundInterval = true;

					startInterval = i;
				}

			} else if(foundInterval) {

				intervals.add(new LineInterval(startInterval, i-startInterval));

				foundInterval = false;
			}

			/*int countDown = 0;

			if(isValid(matrix[1][i])) {
				System.out.println("Found down"+i);
				countDown++;
			}*/

		}	

		for(LineInterval interval: intervals) {

			System.out.println(interval.getStart()+" "+interval.getLength()+" "+interval.getCenter());
		}

	}

	private static boolean isValid(int value) {
		return value > 0;
	}

}
