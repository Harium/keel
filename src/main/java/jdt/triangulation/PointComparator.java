package jdt.triangulation;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.geometry.Point3D;

import java.util.Comparator;

public class PointComparator implements Comparator<Vector3> {

	public PointComparator() {
		super();
	}

	/** compare between two points. */
	public int compare(Vector3 o1, Vector3 o2) {
		int ans = 0;

		if (o1 != null && o2 != null) {
			Vector3 d1 = o1;
			Vector3 d2 = o2;

			if (d1.x > d2.x)
				return 1;
			if (d1.x < d2.x)
				return -1;
			// x1 == x2
			if (d1.y > d2.y)
				return 1;
			if (d1.y < d2.y)
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

	public static boolean isLess(Vector3 a, Vector3 b) {
		return (a.x < b.x) || ((a.x == b.x) && (a.y < b.y));
	}

	public static boolean isGreater(Vector3 a, Vector3 b) {
		return (a.x > b.x) || ((a.x == b.x) && (a.y > b.y));
	}
}
