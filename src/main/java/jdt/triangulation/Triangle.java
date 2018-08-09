package jdt.triangulation;


import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.geometry.BoundingBox;
import com.harium.etyl.geometry.Circle3;

/**
 * This class represents a 3D triangle in a Triangulation! 
 *
 */

public class Triangle {

	protected Vector3 a;
	protected Vector3 b;
	protected Vector3 c;

	protected Triangle abnext,bcnext,canext;
	protected Circle3 circum;

	int modCounter = 0; // modcounter for triangulation fast update.

	boolean halfplane = false; // true iff it is an infinite face.

	boolean mark = false;   // tag - for bfs algorithms
	
	public static int counter = 0, counter2 = 0;
	
	/** constructs a triangle form 3 point - store it in counterclockwised order.*/
	public Triangle(Vector3 a, Vector3 b, Vector3 c) {
		this.a = a;

		if(isClockWise(a,b,c) ) {
			this.b = b;
			this.c = c;
		} else {  // RIGHT
			System.out.println("Warning, ajTriangle(A,B,C) "+
					"expects points in counterclockwise order.");
			System.out.println(""+a+b+c);
			this.b=c;
			this.c=b;
		}
		circumcircle();
	}

	private boolean isClockWise(Vector3 a, Vector3 b, Vector3 c) {
		PointLinePosition res = PointLineTest.pointLineTest(a,b,c);

		return (res == PointLinePosition.LEFT) || (res == PointLinePosition.ON_SEGMENT) ||
				(res == PointLinePosition.INFRONT_OF_A) || (res == PointLinePosition.BEHIND_B);
	}

	/**
	 * creates a half plane using the segment (A,B).
	 * @param A
	 * @param b
	 */
	public Triangle( Vector3 A, Vector3 b) {
		//		visitflag=visitValue;
		this.a = A;
		this.b = b;
		this.c = generateEquilateralPoint(a,b);
		
		halfplane = true;
	}

	public Vector3 generateEquilateralPoint(Vector3 a, Vector3 b) {

		double sin = Math.sin(60 * Math.PI / 180.0);
		double cos = Math.cos(60 * Math.PI / 180.0);

		double mz = (a.z+b.z)/2;

		double cx = cos * (a.x - b.x) - sin * (a.y - b.y) + b.x;
		double cy = sin * (a.x - b.x) + cos * (a.y - b.y) + b.y;

		Vector3 c = new Vector3((float)cx, (float)cy, (float)mz);
		return c;
	}

	/*	protected void finalize() throws Throwable{
		super.finalize();
		_counter--;
	} */

	/** 
	 * remove all pointers (for debug)
	 */
	//public void clear() {
	//	this.abnext = null; this.bcnext=null; this.canext=null;}

	/**
	 * returns true iff this triangle is actually a half plane.
	 */
	public boolean isHalfplane() {return this.halfplane;}
	/**
	 * returns the first vertex of this triangle.
	 */
	public Vector3 p1() {return a;}
	/**
	 * returns the second vertex of this triangle.
	 */
	public Vector3 p2() {return b;}
	/**
	 * returns the 3th vertex of this triangle.
	 */
	public Vector3 p3() {return c;}
	/**
	 * returns the consecutive triangle which shares this triangle p1,p2 edge. 
	 */
	public Triangle next_12() {return this.abnext;} 
	/**
	 * returns the consecutive triangle which shares this triangle p2,p3 edge. 
	 */
	public Triangle next_23() {return this.bcnext;} 
	/**
	 * returns the consecutive triangle which shares this triangle p3,p1 edge. 
	 */
	public Triangle next_31() {return this.canext;} 

	/**
	 * @return  The bounding rectange between the minimum and maximum coordinates
	 *                of the triangle
	 */
	public BoundingBox getBoundingBox() {
		Vector3 lowerLeft, upperRight;
		lowerLeft = new Vector3(Math.min(a.x, Math.min(b.x, c.x)), Math.min(getA().y, Math.min(b.y, c.y)), 0);
		upperRight = new Vector3(Math.max(a.x, Math.max(b.x, c.x)),  Math.max(getA().y, Math.max(b.y, c.y)), 0);
		return new BoundingBox(lowerLeft, upperRight);
	}

	void switchneighbors( Triangle old, Triangle newTriangle ) {
		if ( abnext==old ) {
			abnext=newTriangle;
		} else if ( bcnext==old ) {
			bcnext=newTriangle;
		} else if ( canext==old ) {
			canext=newTriangle;
		}
		else System.out.println( "Error, switchneighbors can't find Old." );
	}

	Triangle neighbor( Vector3 p ) {
		if ( a.equals(p) ) return canext;
		if ( b.equals(p) ) return abnext;
		if ( c.equals(p) ) return bcnext;
		System.out.println( "Error, neighbors can't find p: "+p );
		return null;
	}

	/**
	 * Returns the neighbors that shares the given corner and is not the previous triangle.
	 * @param p The given corner
	 * @param prevTriangle The previous triangle.
	 * @return The neighbors that shares the given corner and is not the previous triangle.
	 * 
	 * By: Eyal Roth & Doron Ganel.
	 */  
	Triangle nextNeighbor(Vector3 p, Triangle prevTriangle) {
		Triangle neighbor = null;

		if (a.equals(p)) {
			neighbor =  canext;
		}
		if (b.equals(p)) {
			neighbor = abnext;
		}
		if (c.equals(p)) {
			neighbor = bcnext;
		}
		
		if(prevTriangle == null) {
			return neighbor;
		}

		// Udi Schneider: Added a condition check for isHalfPlane. If the current
		// neighbor is a half plane, we also want to move to the next neighbor	  
		if (neighbor.equals(prevTriangle) || neighbor.isHalfplane()) {
			if (a.equals(p)) {
				neighbor =  abnext;
			}
			if (b.equals(p)) {
				neighbor = bcnext;
			}
			if (c.equals(p)) {
				neighbor = canext;
			}	  
		}

		return neighbor;
	}

	Circle3 circumcircle() {
		float u = ((a.x-b.x)*(a.x+b.x) + (a.y-b.y)*(a.y+b.y)) / 2.0f;
		float v = ((b.x-c.x)*(b.x+c.x) + (b.y-c.y)*(b.y+c.y)) / 2.0f;
		float den = (a.x-b.x)*(b.y-c.y) - (b.x-c.x)*(a.y-b.y);
		if ( den==0 ) // oops, degenerate case
			circum = new Circle3( a, Float.POSITIVE_INFINITY );
		else {
			Vector3 cen = new Vector3((u*(b.y-c.y) - v*(a.y-b.y)) / den,
					(v*(a.x-b.x) - u*(b.x-c.x)) / den, 0);
			circum = new Circle3( cen, cen.dst(a) );
		}
		return circum;
	}

	boolean circumcircleContains( Vector3 p ) {
		return circum.contains(p);
	}

	public String toString() {
		String res =""; //+_id+") ";
		res += a.toString()+b.toString();
		if ( !halfplane )
			res +=c.toString() ;
		// res +=c.toString() +"   | "+abnext._id+" "+bcnext._id+" "+canext._id;
		return res;
	}

	/**
	 * determinates if this triangle contains the point p.
	 * @param p the query point
	 * @return true iff p is not null and is inside this triangle (Note: on boundary is considered inside!!).
	 */
	public boolean contains(Vector3 p) {
		boolean ans = false;
		if(this.halfplane || p== null) return false;

		if (isCorner(p)) {
			return true;
		}

		PointLinePosition a12 = PointLineTest.pointLineTest(a,b,p);
		PointLinePosition a23 = PointLineTest.pointLineTest(b,c,p);
		PointLinePosition a31 = PointLineTest.pointLineTest(c,a,p);

		if ((a12 == PointLinePosition.LEFT && a23 == PointLinePosition.LEFT && a31 == PointLinePosition.LEFT ) ||
				(a12 == PointLinePosition.RIGHT && a23 == PointLinePosition.RIGHT && a31 == PointLinePosition.RIGHT ) ||
				(a12 == PointLinePosition.ON_SEGMENT ||a23 == PointLinePosition.ON_SEGMENT ||  a31 == PointLinePosition.ON_SEGMENT)) {
			ans = true;
		}

		return ans;
	}

	/**
	 * determinates if this triangle contains the point p.
	 * @param p the query point
	 *  @return true iff p is not null and is inside this triangle (Note: on boundary is considered outside!!).
	 */
	public boolean containsBoundaryIsOutside(Vector3 p) {
		boolean ans = false;
		if(this.halfplane || p == null) return false;

		if (isCorner(p)) {
			return true;
		}

		PointLinePosition a12 = PointLineTest.pointLineTest(a,b,p);
		PointLinePosition a23 = PointLineTest.pointLineTest(b,c,p);
		PointLinePosition a31 = PointLineTest.pointLineTest(c,a,p);

		if ((a12 == PointLinePosition.LEFT && a23 == PointLinePosition.LEFT && a31 == PointLinePosition.LEFT ) ||
				(a12 == PointLinePosition.RIGHT && a23 == PointLinePosition.RIGHT && a31 == PointLinePosition.RIGHT )) {
			ans = true;
		}

		return ans;
	}

	/**
	 * Checks if the given point is a corner of this triangle.
	 * @param p The given point.
	 * @return True iff the given point is a corner of this triangle.
	 * 
	 * By Eyal Roth & Doron Ganel.
	 */
	public boolean isCorner(Vector3 p) {
		return (p == a || p == b || p == c);
	}

	//Doron
	public boolean fallInsideCircumcircle(Vector3[] arrayPoints) {
		boolean isInside = false;
		Vector3 p1 = this.p1();
		Vector3 p2 = this.p2();
		Vector3 p3 = this.p3();
		int i = 0;
		while(!isInside && i<arrayPoints.length)
		{
			Vector3 p = arrayPoints[i];
			if(!p.equals(p1)&& !p.equals(p2) && !p.equals(p3))
			{
				isInside = this.circumcircleContains(p);
			}
			i++;
		}

		return isInside;
	}

	/**
	 * compute the Z value for the X,Y values of q. <br />
	 * assume this triangle represent a plane --> q does NOT need to be contained
	 * in this triangle.
	 * 
	 * @param q query point (its Z value is ignored).
	 * @return the Z value of this plane implies by this triangle 3 points.
	 */
	public float z_value(Vector3 q) {
		if(q==null || this.halfplane) throw new RuntimeException("*** ERR wrong parameters, can't approximate the z value ..***: "+q);
		/* incase the query point is on one of the points */
		if(q.x==a.x & q.y==a.y) return a.z;
		if(q.x==b.x & q.y==b.y) return b.z;
		if(q.x==c.x & q.y==c.y) return c.z;

		/* 
		 *  plane: aX + bY + c = Z:
		 *  2D line: y= mX + k
		 *  
		 */
		float x=0,x0 = q.x, x1 = a.x, x2=b.x, x3=c.x;
		float y=0,y0 = q.y, y1 = a.y, y2=b.y, y3=c.y;
		float z=0, m01=0,k01=0,m23=0,k23=0;

		float r = 0;

		// 0 - regular, 1-horizontal , 2-vertical.
		int flag01 = 0;
		if(x0!=x1) {
			m01 = (y0-y1)/(x0-x1);
			k01 = y0 - m01*x0;
			if (m01 ==0) {
				flag01 = 1;
			}
		} else { // 2-vertical.
			flag01 = 2;//x01 = x0
		}
		int flag23 = 0;
		if(x2!=x3) {
			m23 = (y2-y3)/(x2-x3);
			k23 = y2 - m23*x2;
			if (m23 ==0) {
				flag23 = 1;
			}
		}
		else { // 2-vertical.
			flag23 = 2;//x01 = x0
		}

		if (flag01 ==2 ) {
			x = x0;
			y = m23*x + k23;
		}
		else {
			if(flag23==2) {
				x = x2;
				y = m01*x + k01;
			}
			else {  // regular case 
				x=(k23-k01)/(m01-m23);
				y = m01*x+k01;

			}
		}
		if(flag23==2) {
			r=(y2-y)/(y2-y3);
		} else {
			r=(x2-x)/(x2-x3);
		}

		z = b.z + (c.z-b.z)*r;
		if(flag01==2) {
			r=(y1-y0)/(y1-y);
		} else {
			r=(x1-x0)/(x1-x);
		}
		float qZ = a.z + (z-a.z)*r;
		return qZ;
	}

	/**
	 * compute the Z value for the X,Y values of q.
	 * assume this triangle represent a plane --> q does NOT need to be contained
	 * in this triangle.
	 *   
	 * @param x  x-coordinate of the query point.
	 * @param y  y-coordinate of the query point.
	 * @return z (height) value approximation given by the triangle it falls in.
	 * 
	 */
	public double z(float x, float y) {
		return z_value(new Vector3(x,y, 0));
	}
	/**
	 * compute the Z value for the X,Y values of q.
	 * assume this triangle represent a plane --> q does NOT need to be contained
	 * in this triangle.
	 *   
	 * @param q query point (its Z value is ignored).
	 * @return q with updated Z value.
	 * 
	 */
	public Vector3 z(Vector3 q) {
		float z = z_value(q);
		return new Vector3(q.x, q.y, z);
	}

	public Vector3 getA() {
		return a;
	}

	public Vector3 getB() {
		return b;
	}

	public Vector3 getC() {
		return c;
	}

	//checks if the triangle is not re-entrant
	public static float calcDet(Vector3 a ,Vector3 b, Vector3 c) {
		return (a.x*(b.y-c.y)) - (a.y*(b.x-c.x)) + (b.x*c.y-b.y*c.x);
	}

	public double calcDet() {
		return Triangle.calcDet(a, b, c);
	}

	/**
	 * checks if the 2 triangles shares a segment
	 * @author Doron Ganel & Eyal Roth(2009)
	 * @param t2 - a second triangle
	 * @return boolean
	 */
	public boolean shareSegment(Triangle t2) {
		return sharedSegments(t2)>=2;
	}

	/**
	 * checks if the 2 triangles shares a segment
	 * @author Doron Ganel & Eyal Roth(2009)
	 * @param t2 - a second triangle
	 * @return boolean
	 */
	public int sharedSegments(Triangle t2) {
		int counter = 0;

		if(a.equals(t2.a)) {
			counter++;
		}
		if(a.equals(t2.b)) {
			counter++;
		}
		if(a.equals(t2.c)) {
			counter++;
		}
		if(b.equals(t2.a)) {
			counter++;
		}
		if(b.equals(t2.b)) {
			counter++;
		}
		if(b.equals(t2.c)) {
			counter++;
		}
		if(c.equals(t2.a)) {
			counter++;
		}
		if(c.equals(t2.b)) {
			counter++;
		}
		if(c.equals(t2.c)) {
			counter++;
		}

		return counter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		return equals((Triangle) obj);
	}

	public boolean equals(Triangle other) {

		if (a == null) {
			if (other.a != null) {
				return false;
			}
		} else if (!a.equals(other.a)&&!a.equals(other.b)&&!a.equals(other.c)) {
			return false;
		}

		if (b == null) {
			if (other.b != null) {
				return false;
			}
		} else if (!b.equals(other.a)&&!b.equals(other.b)&&!b.equals(other.c)) {
			return false;
		}

		if (c == null) {
			if (other.c != null) {
				return false;
			}
		} else if (!c.equals(other.a)&&!c.equals(other.b)&&!c.equals(other.c)) {
			return false;
		}

		return true;
	}

}
