package jdt.triangulation;

import java.util.Comparator;

import br.com.etyllica.core.linear.Point3D;

public class PointComparator implements Comparator<Point3D> {

	public PointComparator() {
		super();
	}

	/** compare between two points. */
	public int compare(Point3D o1, Point3D o2) {
		int ans = 0;

		if (o1 != null && o2 != null) {
			Point3D d1 = o1;
			Point3D d2 = o2;

			if (d1.getX() > d2.getX())
				return 1;
			if (d1.getX() < d2.getX())
				return -1;
			// x1 == x2
			if (d1.getY() > d2.getY())
				return 1;
			if (d1.getY() < d2.getY())
				return -1;
		} else {
			if (o1 == null && o2 == null)
				return 0;
			if (o1 == null && o2 != null)
				return 1;
			if (o1 != null && o2 == null)
				return -1;
		}
		
		return ans;
	}

	public boolean equals(Point3D ob) {
		return false;
	}

	public static boolean isLess(Point3D a, Point3D b) {
		return (a.getX() < b.getX()) || ((a.getX() == b.getX()) && (a.getY() < b.getY()));
	}

	public static boolean isGreater(Point3D a, Point3D b) {
		return (a.getX() > b.getX()) || ((a.getX() == b.getX()) && (a.getY() > b.getY()));
	}
}
