package jdt.triangulation;

import br.com.etyllica.core.linear.Point3D;

//pointLineTest
// ===============
// simple geometry to make things easy!
public class PointLineTest {
	public final static int ONSEGMENT = 0;

	/**
	 * + <br>
	 * �����a---------b������
	 * */
	public final static int LEFT = 1;

	/**
	 * �����a---------b������ <br>
	 * +
	 * */
	public final static int RIGHT = 2;
	/** ��+��a---------b������ */
	public final static int INFRONTOFA = 3;
	/** ������a---------b���+��� */
	public final static int BEHINDB = 4;
	public final static int ERROR = 5;
	
	/**
	 * tests the relation between this point (as a 2D [x,y] point) and a 2D
	 * segment a,b (the Z values are ignored), returns one of the following:
	 * LEFT, RIGHT, INFRONTOFA, BEHINDB, ONSEGMENT
	 * 
	 * @param a
	 *            the first point of the segment.
	 * @param b
	 *            the second point of the segment.
	 * @return the value (flag) of the relation between this point and the a,b
	 *         line-segment.
	 */
	public static int pointLineTest(Point3D a, Point3D b, Point3D c) {

		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		double res = dy * (c.getX() - a.getX()) - dx * (c.getY() - a.getY());

		if (res < 0)
			return PointLineTest.LEFT;
		if (res > 0)
			return PointLineTest.RIGHT;

		if (dx > 0) {
			if (c.getX() < a.getX())
				return PointLineTest.INFRONTOFA;
			if (b.getX() < c.getX())
				return PointLineTest.BEHINDB;
			return PointLineTest.ONSEGMENT;
		}
		if (dx < 0) {
			if (c.getX() > a.getX())
				return PointLineTest.INFRONTOFA;
			if (b.getX() > c.getX())
				return PointLineTest.BEHINDB;
			return PointLineTest.ONSEGMENT;
		}
		if (dy > 0) {
			if (c.getY() < a.getY())
				return PointLineTest.INFRONTOFA;
			if (b.getY() < c.getY())
				return PointLineTest.BEHINDB;
			return PointLineTest.ONSEGMENT;
		}
		if (dy < 0) {
			if (c.getY() > a.getY())
				return PointLineTest.INFRONTOFA;
			if (b.getY() > c.getY())
				return PointLineTest.BEHINDB;
			return PointLineTest.ONSEGMENT;
		}
		System.out.println("Error, pointLineTest with a=b");
		return PointLineTest.ERROR;
	}
	
}
