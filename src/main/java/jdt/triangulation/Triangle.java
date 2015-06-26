package jdt.triangulation;

import br.com.etyllica.linear.BoundingBox;
import br.com.etyllica.linear.Circle;
import br.com.etyllica.linear.Point3D;

/**
 * This class represents a 3D triangle in a Triangulation! 
 *
 */

public class Triangle {
	
	protected Point3D a;
	protected Point3D b;
	protected Point3D c;

	protected Triangle abnext,bcnext,canext;
	protected Circle circum;
	
	int modCounter = 0; // modcounter for triangulation fast update.

	boolean halfplane = false; // true iff it is an infinite face.

	//	public boolean visitflag;
	boolean mark = false;   // tag - for bfs algorithms
	//	private static boolean visitValue=false;
	public static int counter = 0, counter2 = 0;
	//public int _id;
	/** constructs a triangle form 3 point - store it in counterclockwised order.*/
	public Triangle( Point3D A, Point3D B, Point3D C ) {
		//		visitflag=visitValue;
		a = A;
		int res = PointLineTest.pointLineTest(A,B,C);
		if ( (res <= PointLineTest.LEFT) ||
				(res == PointLineTest.INFRONTOFA) ||
				(res == PointLineTest.BEHINDB) ) {
			b=B;
			c=C;
		}
		else {  // RIGHT
			System.out.println("Warning, ajTriangle(A,B,C) "+
					"expects points in counterclockwise order.");
			System.out.println(""+A+B+C);
			b=C;
			c=B;
		}
		circumcircle();
		//_id = _counter++;
		//_counter++;_c2++;
		//if(_counter%10000 ==0) System.out.println("Triangle: "+_counter);
	}

	/**
	 * creates a half plane using the segment (A,B).
	 * @param A
	 * @param B
	 */
	public Triangle( Point3D A, Point3D B ) {
		//		visitflag=visitValue;
		a = A;
		b = B;
		
		c = generateEquilateralPoint(a,b);
		halfplane = true;
		//		_id = _counter++;
	}

	private Point3D generateEquilateralPoint(Point3D a, Point3D b) {

		double sin = Math.sin(60 * Math.PI / 180.0);
		double cos = Math.cos(60 * Math.PI / 180.0);
		
		double mz = (a.getZ()+b.getZ())/2;
		
		double cx = cos * (a.getX() - b.getX()) - sin * (a.getY() - b.getY()) + b.getX();
		double cy = sin * (a.getX() - b.getX()) + cos * (a.getY() - b.getY()) + b.getY(); 
		
		Point3D c = new Point3D(cx, cy, mz);
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
	public Point3D p1() {return a;}
	/**
	 * returns the second vertex of this triangle.
	 */
	public Point3D p2() {return b;}
	/**
	 * returns the 3th vertex of this triangle.
	 */
	public Point3D p3() {return c;}
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
		Point3D lowerLeft, upperRight;
		lowerLeft = new Point3D(Math.min(getA().getX(), Math.min(b.getX(), c.getX())), Math.min(getA().getY(), Math.min(b.getY(), c.getY())));
		upperRight = new Point3D(Math.max(getA().getX(), Math.max(b.getX(), c.getX())),  Math.max(getA().getY(), Math.max(b.getY(), c.getY())));
		return new BoundingBox(lowerLeft, upperRight);
	}

	void switchneighbors( Triangle Old,Triangle New ) {
		if ( abnext==Old ) abnext=New;
		else if ( bcnext==Old ) bcnext=New;
		else if ( canext==Old ) canext=New;
		else System.out.println( "Error, switchneighbors can't find Old." );
	}

	Triangle neighbor( Point3D p ) {
		if ( a==p ) return canext;
		if ( b==p ) return abnext;
		if ( c==p ) return bcnext;
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
	Triangle nextNeighbor(Point3D p, Triangle prevTriangle) {
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

	Circle circumcircle() {

		double u = ((a.getX()-b.getX())*(a.getX()+b.getX()) + (a.getY()-b.getY())*(a.getY()+b.getY())) / 2.0f;
		double v = ((b.getX()-c.getX())*(b.getX()+c.getX()) + (b.getY()-c.getY())*(b.getY()+c.getY())) / 2.0f;
		double den = (a.getX()-b.getX())*(b.getY()-c.getY()) - (b.getX()-c.getX())*(a.getY()-b.getY());
		if ( den==0 ) // oops, degenerate case
			circum = new Circle( a,Double.POSITIVE_INFINITY );
		else {
			Point3D cen = new Point3D((u*(b.getY()-c.getY()) - v*(a.getY()-b.getY())) / den,
					(v*(a.getX()-b.getX()) - u*(b.getX()-c.getX())) / den);
			circum = new Circle( cen, cen.distanceXY(a) );
		}
		return circum;
	}

	boolean circumcircleContains( Point3D p ) {
		return circum.getRadius() > circum.getCenter().distanceXY(p);
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
	public boolean contains(Point3D p) {
		boolean ans = false;
		if(this.halfplane || p== null) return false;

		if (isCorner(p)) {
			return true;
		}

		int a12 = PointLineTest.pointLineTest(a,b,p);
		int a23 = PointLineTest.pointLineTest(b,c,p);
		int a31 = PointLineTest.pointLineTest(c,a,p);

		if ((a12 == PointLineTest.LEFT && a23 == PointLineTest.LEFT && a31 == PointLineTest.LEFT ) ||
				(a12 == PointLineTest.RIGHT && a23 == PointLineTest.RIGHT && a31 == PointLineTest.RIGHT ) ||	
				(a12 == PointLineTest.ONSEGMENT ||a23 == PointLineTest.ONSEGMENT ||  a31 == PointLineTest.ONSEGMENT))
			ans = true;

		return ans;
	}

	/**
	 * determinates if this triangle contains the point p.
	 * @param p the query point
	 *  @return true iff p is not null and is inside this triangle (Note: on boundary is considered outside!!).
	 */
	public boolean containsBoundaryIsOutside(Point3D p) {
		boolean ans = false;
		if(this.halfplane || p == null) return false;

		if (isCorner(p)) {
			return true;
		}

		int a12 = PointLineTest.pointLineTest(a,b,p);
		int a23 = PointLineTest.pointLineTest(b,c,p);
		int a31 = PointLineTest.pointLineTest(c,a,p);

		if ((a12 == PointLineTest.LEFT && a23 == PointLineTest.LEFT && a31 == PointLineTest.LEFT ) ||
				(a12 == PointLineTest.RIGHT && a23 == PointLineTest.RIGHT && a31 == PointLineTest.RIGHT ))
			ans = true;

		return ans;
	}

	/**
	 * Checks if the given point is a corner of this triangle.
	 * @param p The given point.
	 * @return True iff the given point is a corner of this triangle.
	 * 
	 * By Eyal Roth & Doron Ganel.
	 */
	public boolean isCorner(Point3D p) {
		return (p == a || p == b || p == c);
	}

	//Doron
	public boolean fallInsideCircumcircle(Point3D[] arrayPoints) {	
		boolean isInside = false; 
		Point3D p1 = this.p1();
		Point3D p2 = this.p2();
		Point3D p3 = this.p3();
		int i = 0;
		while(!isInside && i<arrayPoints.length)
		{
			Point3D p = arrayPoints[i];
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
	public double z_value(Point3D q) {
		if(q==null || this.halfplane) throw new RuntimeException("*** ERR wrong parameters, can't approximate the z value ..***: "+q);
		/* incase the query point is on one of the points */
		if(q.getX()==a.getX() & q.getY()==a.getY()) return a.getZ();
		if(q.getX()==b.getX() & q.getY()==b.getY()) return b.getZ();
		if(q.getX()==c.getX() & q.getY()==c.getY()) return c.getZ();

		/* 
		 *  plane: aX + bY + c = Z:
		 *  2D line: y= mX + k
		 *  
		 */
		double X=0,x0 = q.getX(), x1 = a.getX(), x2=b.getX(), x3=c.getX();
		double Y=0,y0 = q.getY(), y1 = a.getY(), y2=b.getY(), y3=c.getY();
		double Z=0, m01=0,k01=0,m23=0,k23=0;

		// 0 - regular, 1-horisintal , 2-vertical.
		int flag01 = 0;
		if(x0!=x1) {
			m01 = (y0-y1)/(x0-x1);
			k01 = y0 - m01*x0;
			if(m01 ==0) flag01 = 1;
		}
		else { // 2-vertical.
			flag01 = 2;//x01 = x0
		}
		int flag23 = 0;
		if(x2!=x3) {
			m23 = (y2-y3)/(x2-x3);
			k23 = y2 - m23*x2;
			if(m23 ==0) flag23 = 1;
		}
		else { // 2-vertical.
			flag23 = 2;//x01 = x0
		}

		if(flag01 ==2 ) {
			X = x0;
			Y = m23*X + k23;
		}
		else {
			if(flag23==2) {
				X = x2;
				Y = m01*X + k01;
			}
			else {  // regular case 
				X=(k23-k01)/(m01-m23);
				Y = m01*X+k01;

			}
		}
		double r = 0;
		if(flag23==2) {
			r=(y2-Y)/(y2-y3);
		} else {
			r=(x2-X)/(x2-x3);
		}

		Z = b.getZ() + (c.getZ()-b.getZ())*r;
		if(flag01==2) {
			r=(y1-y0)/(y1-Y);
		} else {
			r=(x1-x0)/(x1-X);
		}
		double qZ = a.getZ() + (Z-a.getZ())*r;
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
	public double z(double x, double y) {
		return z_value(new Point3D(x,y));
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
	public Point3D z(Point3D q) {
		double z = z_value(q);
		return new Point3D(q.getX(),q.getY(), z);
	}
	
	public Point3D getA() {
		return a;
	}

	public Point3D getB() {
		return b;
	}

	public Point3D getC() {
		return c;
	}

	//checks if the triangle is not re-entrant
	public static double calcDet(Point3D a ,Point3D b, Point3D c) {
		return (a.getX()*(b.getY()-c.getY())) - (a.getY()*(b.getX()-c.getX())) + (b.getX()*c.getY()-b.getY()*c.getX());  
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
		Triangle other = (Triangle) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a)||!a.equals(other.b)||!a.equals(other.c))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b)||!b.equals(other.c))
			return false;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		return true;
	}
	
}
