package jdt.triangulation;

import com.harium.etyl.linear.BoundingBox;
import com.harium.etyl.linear.Point3D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 *
 * This class represents a Delaunay Triangulation. The class was written for a
 * large scale triangulation (1000 - 200,000 vertices). The application main use is 3D surface (terrain) presentation.
 * <br>
 * The class main properties are the following:<br>
 * - fast point location. (O(n^0.5)), practical runtime is often very fast. <br>
 * - handles degenerate cases and none general examples.position input (ignores duplicate points). <br>
 * - save & load from\to text file in TSIN format. <br>
 * - 3D support: including z value approximation. <br>
 * - standard java (1.5 generic) iterators for the vertices and triangles. <br>
 * - smart iterator to only the updated triangles - for terrain simplification <br>
 * <br>
 *
 * Testing (done in early 2005): Platform java 1.5.02 windows XP (SP2), AMD laptop 1.6G sempron CPU
 * 512MB RAM. Constructing a triangulation of 100,000 vertices takes ~ 10
 * seconds. point location of 100,000 points on a triangulation of 100,000
 * vertices takes ~ 5 seconds.
 *
 * Note: constructing a triangulation with 200,000 vertices and more requires
 * extending java heap size (otherwise an exception will be thrown).<br>
 *
 * Bugs: if U find a bug or U have an idea as for how to improve the code,
 * please send me an email to: benmo@ariel.ac.il
 *
 * @author Boaz Ben Moshe 5/11/05 <br>
 * The project uses some ideas presented in the VoroGuide project, written by Klasse f?r Kreise (1996-1997),
 * For the original applet see: http://www.pi6.fernuni-hagen.de/GeomLab/VoroGlide/ . <br>
 */

public class DelaunayTriangulation {

	// the first and last points (used only for first step construction)
	private Point3D firstP;
	private Point3D lastP;

	// for degenerate case!
	private boolean allCollinear;

	// the first and last triangles (used only for first step construction)
	private Triangle firstT, lastT;

	// the triangle the fond (search start from
	private Triangle startTriangle;

	// the triangle the convex hull starts from
	public Triangle startTriangleHull;

	// additional data 4/8/05 used by the iterators
	//private Set<Point3D> vertices;
	//private List<Triangle> triangles;

	// The triangles that were deleted in the last deletePoint iteration.
	//private List<Triangle> deletedTriangles;
	// The triangles that were added in the last deletePoint iteration.
	//private List<Triangle> addedTriangles;

	private int modCount = 0, modCount2 = 0;

	// the Bounding Box, {{x0,y0,z0} , {x1,y1,z1}}
	private Point3D bbMin, bbMax;

	/**
	 * Index for faster point location searches
	 */
	//private GridIndex gridIndex = null;

	/**
	 * Constructor: creates a Delaunay Triangulation.
	 */
	public DelaunayTriangulation() {
		init(64);
	}

	private void init(int size) {
		modCount = 0;
		modCount2 = 0;
		allCollinear = true;
	}

	/**
	 * the number of (different) vertices in this triangulation.
	 *
	 * @return the number of vertices in the triangulation (duplicates are
	 *         ignore - set size).
	 */
	/*public int size() {
		return vertices.size();
	}*/

	/**
	 * @return the number of triangles in the triangulation. <br />
	 * Note: includes infinife faces!!.
	 */
	/*public int trianglesSize() {
		this.generateTriangles();
		return triangles.size();
	}*/

	/**
	 * returns the changes counter for this triangulation
	 */
	public int getModeCounter() {
		return this.modCount;
	}

	/**
	 * insert the point to this Delaunay Triangulation. Note: if p is null or
	 * already exist in this triangulation p is ignored.
	 * @param vertices 
	 * @param p new vertex to be inserted the triangulation.
	 */
	public void insertPoint(Set<Point3D> vertices, Point3D p) {

		modCount++;
		updateBoundingBox(p);
		vertices.add(p);
		Triangle t = insertPointSimple(vertices, p);

		if (t == null) //
			return;

		Triangle tt = t;
		//currT = t; // recall the last point for - fast (last) update iterator.
		do {
			flip(tt, modCount);
			tt = tt.canext;
		} while (tt != t && !tt.halfplane);

		// Update index with changed triangles
		/*if(gridIndex != null) {
			gridIndex.updateIndex(getLastUpdatedTriangles());
		}*/
	}

	/**
	 * Deletes the given point from this.
	 * @param pointToDelete The given point to delete.
	 * 
	 * Implementation of the Mostafavia, Gold & Dakowicz algorithm (2002).
	 * 
	 * By Eyal Roth & Doron Ganel (2009).
	 */
	/*public void deletePoint(Point3D pointToDelete) {

		// Finding the triangles to delete.
		List<Triangle> deletedTriangles = findConnectedTriangles(pointToDelete);

		Set<Point3D> pointsSet = new HashSet<Point3D>();
		List<Point3D> pointsVec = new ArrayList<Point3D>();

		if (deletedTriangles != null) {
			connectTriangles(pointToDelete, pointsSet, pointsVec, triangles);
		} else {
			System.err.println("Error: can't delete a point on the perimeter");
			return;
		}

		while(pointsVec.size() >= 3) {
			// Getting a triangle to add, and saving it.
			Triangle triangle = findTriangle(pointsVec, pointToDelete);
			addedTriangles.add(triangle);

			// Finding the point on the diagonal (pointToDelete,p)
			Point3D p = findDiagonal(triangle,pointToDelete);

			for(Point3D tmpP : pointsVec) {
				if(tmpP.equals(p)) {
					pointsVec.remove(tmpP);
					break;
				}
			}
		}

		//updating the trangulation
		deleteUpdate(pointToDelete, deletedTriangles);

		for(Triangle t:deletedTriangles) {
			if(t.equals(startTriangle)) {
				startTriangle = addedTriangles.get(0);
				break;
			}
		}

		triangles.removeAll(deletedTriangles);
		triangles.addAll(addedTriangles);
		vertices.remove(pointToDelete);

		addedTriangles.clear();
	}*/

	/** return a point from the trangulation that is close to pointToDelete
	 * @param pointToDelete the point that the user wants to delete
	 * @return a point from the trangulation that is close to pointToDelete
	 * By Eyal Roth & Doron Ganel (2009).
	 */
	public Point3D findClosePoint(Point3D pointToDelete) {
		Triangle triangle = find(pointToDelete);
		Point3D p1 = triangle.p1();
		Point3D p2 = triangle.p2();
		double d1 = p1.distanceXY(pointToDelete);
		double d2 = p2.distanceXY(pointToDelete);
		if(triangle.isHalfplane()) {
			if(d1<=d2) {
				return p1;
			}
			else {
				return p2;
			}
		} else {
			Point3D p3 = triangle.p3();

			double d3 = p3.distanceXY(pointToDelete);

			if(d1<=d2 && d1<=d3) {
				return p1;
			}
			else if(d2<=d1 && d2<=d3) {
				return p2;
			}
			else {
				return p3;
			}
		}
	}

	//updates the trangulation after the triangles to be deleted and
	//the triangles to be added were found
	//by Doron Ganel & Eyal Roth(2009)
	/*private void deleteUpdate(Point3D pointToDelete, List<Triangle> deletedTriangles) {

		for(int j = 0; j < addedTriangles.size(); j++) {

			Triangle addedTriangle1 = addedTriangles.get(j);

			for(Triangle deletedTriangle : deletedTriangles) {
				if(addedTriangle1.shareSegment(deletedTriangle)) {
					updateNeighbor(addedTriangle1,deletedTriangle,pointToDelete);
				}
			}

			for(int i = 0; i < addedTriangles.size(); i++) {
				Triangle addedTriangle2 = addedTriangles.get(i);
				if(addedTriangle1.equals(addedTriangle2)) {
					continue;
				}

				if(addedTriangle1.shareSegment(addedTriangle2)) {
					updateNeighbor(addedTriangle1,addedTriangle2);
				}
			}
		}

		// Update index with changed triangles
		if(gridIndex != null) {
			gridIndex.updateIndex(addedTriangles.iterator());
		}
	}*/

	//update the neighbors of the addedTriangle and deletedTriangle
	//we assume the 2 triangles share a segment
	//by Doron Ganel & Eyal Roth(2009)
	/*private void updateNeighbor(Triangle addedTriangle, Triangle deletedTriangle,Point3D pointToDelete) {
		Point3D delA = deletedTriangle.p1();
		Point3D delB = deletedTriangle.p2();
		Point3D delC = deletedTriangle.p3();
		Point3D addA = addedTriangle.p1();
		Point3D addB = addedTriangle.p2();
		Point3D addC = addedTriangle.p3();

		//updates the neighbor of the deleted triangle to point to the added triangle
		//setting the neighbor of the added triangle
		if(pointToDelete.equals(delA)) {
			deletedTriangle.next_23().switchneighbors(deletedTriangle,addedTriangle);
			//AB-BC || BA-BC
			if((addA.equals(delB)&&addB.equals(delC)) || (addB.equals(delB)&&addA.equals(delC))) {                    
				addedTriangle.abnext = deletedTriangle.next_23();                
			}
			//AC-BC || CA-BC
			else if((addA.equals(delB)&&addC.equals(delC)) || (addC.equals(delB)&&addA.equals(delC))) {                    
				addedTriangle.canext = deletedTriangle.next_23();                
			}
			//BC-BC || CB-BC
			else {
				addedTriangle.bcnext = deletedTriangle.next_23();
			}
		}
		else if(pointToDelete.equals(delB)) {
			deletedTriangle.next_31().switchneighbors(deletedTriangle,addedTriangle);
			//AB-AC || BA-AC
			if((addA.equals(delA)&&addB.equals(delC)) || (addB.equals(delA)&&addA.equals(delC))) {                    
				addedTriangle.abnext = deletedTriangle.next_31();                
			}
			//AC-AC || CA-AC
			else if((addA.equals(delA)&&addC.equals(delC)) || (addC.equals(delA)&&addA.equals(delC))) {                    
				addedTriangle.canext = deletedTriangle.next_31();                
			}
			//BC-AC || CB-AC
			else {
				addedTriangle.bcnext = deletedTriangle.next_31();
			}
		}
		//equals c
		else {
			deletedTriangle.next_12().switchneighbors(deletedTriangle,addedTriangle);
			//AB-AB || BA-AB
			if((addA.equals(delA)&&addB.equals(delB)) || (addB.equals(delA)&&addA.equals(delB))) {                    
				addedTriangle.abnext = deletedTriangle.next_12();                
			}
			//AC-AB || CA-AB
			else if((addA.equals(delA)&&addC.equals(delB)) || (addC.equals(delA)&&addA.equals(delB))) {                    
				addedTriangle.canext = deletedTriangle.next_12();                
			}
			//BC-AB || CB-AB
			else {
				addedTriangle.bcnext = deletedTriangle.next_12();
			}
		}
	}*/

	//update the neighbors of the 2 added Triangle s
	//we assume the 2 triangles share a segment
	//by Doron Ganel & Eyal Roth(2009)
	/*private void updateNeighbor(Triangle addedTriangle1,Triangle addedTriangle2) {
		Point3D A1 = addedTriangle1.p1();
		Point3D B1 = addedTriangle1.p2();
		Point3D C1 = addedTriangle1.p3();
		Point3D A2 = addedTriangle2.p1();
		Point3D B2 = addedTriangle2.p2();
		Point3D C2 = addedTriangle2.p3();

		//A1-A2
		if(A1.equals(A2)) {
			//A1B1-A2B2
			if(B1.equals(B2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//A1B1-A2C2
			else if(B1.equals(C2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//A1C1-A2B2
			else if(C1.equals(B2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//A1C1-A2C2
			else {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
		}
		//A1-B2
		else if(A1.equals(B2)) {
			//A1B1-B2A2
			if(B1.equals(A2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//A1B1-B2C2
			else if(B1.equals(C2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
			//A1C1-B2A2
			else if(C1.equals(A2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//A1C1-B2C2
			else {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
		}
		//A1-C2
		else if(A1.equals(C2)) {
			//A1B1-C2A2
			if(B1.equals(A2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//A1B1-C2B2
			if(B1.equals(B2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
			//A1C1-C2A2
			if(C1.equals(A2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//A1C1-C2B2
			else {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
		}
		//B1-A2
		else if(B1.equals(A2)) {
			//B1A1-A2B2
			if(A1.equals(B2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//B1A1-A2C2
			else if(A1.equals(C2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//B1C1-A2B2
			else if(C1.equals(B2)) {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//B1C1-A2C2
			else {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
		}
		//B1-B2
		else if(B1.equals(B2)) {
			//B1A1-B2A2
			if(A1.equals(A2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//B1A1-B2C2
			else if(A1.equals(C2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
			//B1C1-B2A2
			else if(C1.equals(A2)) {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//B1C1-B2C2
			else {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
		}
		//B1-C2
		else if(B1.equals(C2)) {
			//B1A1-C2A2
			if(A1.equals(A2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//B1A1-C2B2
			if(A1.equals(B2)) {
				addedTriangle1.abnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
			//B1C1-C2A2
			if(C1.equals(A2)) {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//B1C1-C2B2
			else {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
		}
		//C1-A2
		else if(C1.equals(A2)) {
			//C1A1-A2B2
			if(A1.equals(B2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//C1A1-A2C2
			else if(A1.equals(C2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//C1B1-A2B2
			else if(B1.equals(B2)) {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//C1B1-A2C2
			else {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
		}
		//C1-B2
		else if(C1.equals(B2)) {
			//C1A1-B2A2
			if(A1.equals(A2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//C1A1-B2C2
			else if(A1.equals(C2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
			//C1B1-B2A2
			else if(B1.equals(A2)) {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.abnext = addedTriangle1;
			}
			//C1B1-B2C2
			else {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
		}
		//C1-C2
		else if(C1.equals(C2)) {
			//C1A1-C2A2
			if(A1.equals(A2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//C1A1-C2B2
			if(A1.equals(B2)) {
				addedTriangle1.canext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
			//C1B1-C2A2
			if(B1.equals(A2)) {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.canext = addedTriangle1;
			}
			//C1B1-C2B2
			else {
				addedTriangle1.bcnext = addedTriangle2;
				addedTriangle2.bcnext = addedTriangle1;
			}
		}
	}*/

	//finds the a point on the triangle that if connect it to "point" (creating a segment)
	//the other two points of the triangle will be to the left and to the right of the segment
	//by Doron Ganel & Eyal Roth(2009)
	/*private Point3D findDiagonal(Triangle triangle, Point3D point) {
		Point3D p1 = triangle.p1();
		Point3D p2 = triangle.p2();
		Point3D p3 = triangle.p3();

		if((PointLineTest.pointLineTest(point, p3, p1) == PointLineTest.LEFT)&&
				(PointLineTest.pointLineTest(point, p3, p2) == PointLineTest.RIGHT))
			return p3;
		if((PointLineTest.pointLineTest(point,p2,p3) == PointLineTest.LEFT)&&
				(PointLineTest.pointLineTest(point,p2, p1) == PointLineTest.RIGHT))
			return p2;
		if((PointLineTest.pointLineTest(point, p1, p2) == PointLineTest.LEFT)&&
				(PointLineTest.pointLineTest(point, p1, p3) == PointLineTest.RIGHT))
			return p1;
		return null;
	}*/

	/**
	 * Calculates a Voronoi cell for a given neighborhood
	 * in this triangulation. A neighborhood is defined by a triangle
	 * and one of its corner points.
	 *  
	 * By Udi Schneider
	 * 
	 * @param triangle a triangle in the neighborhood  
	 * @param p corner point whose surrounding neighbors will be checked
	 * @return set of Points representing the cell polygon
	 */
	public Point3D[] calcVoronoiCell(Triangle triangle, Point3D p) {
		// handle any full triangle 
		if (!triangle.isHalfplane()) {

			// get all neighbors of given corner point
			List<Triangle> neighbors = findTriangleNeighborhood(triangle, p);

			Iterator<Triangle> itn = neighbors.iterator();
			Point3D[] vertices = new Point3D[neighbors.size()];

			// for each neighbor, including the given triangle, add
			// center of circumscribed circle to cell polygon
			int index = 0;
			while (itn.hasNext()) {
				Triangle tmp = itn.next();									
				vertices[index++] = tmp.circumcircle().getCenter();				
			}			

			return vertices;
		}

		// handle half plane
		// in this case, the cell is a single line
		// which is the perpendicular bisector of the half plane line
		else {
			// local friendly alias			
			Triangle halfplane = triangle;
			// third point of triangle adjacent to this half plane
			// (the point not shared with the half plane)
			Point3D third = null;
			// triangle adjacent to the half plane
			Triangle neighbor = null;

			// find the neighbor triangle
			if (!halfplane.next_12().isHalfplane()) {
				neighbor = halfplane.next_12();				
			}
			else if (!halfplane.next_23().isHalfplane()) {
				neighbor = halfplane.next_23();				
			}
			else if (!halfplane.next_23().isHalfplane()) {
				neighbor = halfplane.next_31();				
			}

			// find third point of neighbor triangle
			// (the one which is not shared with current half plane)
			// this is used in determining half plane orientation
			if (!neighbor.p1().equals(halfplane.p1()) && !neighbor.p1().equals(halfplane.p2()) ) 
				third = neighbor.p1();  
			if (!neighbor.p2().equals(halfplane.p1()) && !neighbor.p2().equals(halfplane.p2()) ) 
				third = neighbor.p2();
			if (!neighbor.p3().equals(halfplane.p1()) && !neighbor.p3().equals(halfplane.p2()) ) 
				third = neighbor.p3();

			// delta (slope) of half plane edge
			double halfplane_delta = (halfplane.p1().getY() - halfplane.p2().getY()) /
					(halfplane.p1().getX() - halfplane.p2().getX());

			// delta of line perpendicular to current half plane edge
			double perp_delta = (1.0 / halfplane_delta) * (-1.0);

			// determine orientation: find if the third point of the triangle
			// lies above or below the half plane
			// works by finding the matching y value on the half plane line equation
			// for the same x value as the third point
			double y_orient =  halfplane_delta * (third.getX() - halfplane.p1().getX()) + halfplane.p1().getY();
			boolean above = true;
			if (y_orient > third.getY())
				above = false;

			// based on orientation, determine cell line direction
			// (towards right or left side of window)
			double sign = 1.0;
			if ((perp_delta < 0 && !above) || (perp_delta > 0 && above))
				sign = -1.0;

			// the cell line is a line originating from the circumcircle to infinity
			// x = 500.0 is used as a large enough value
			Point3D circumcircle = neighbor.circumcircle().getCenter();
			double x_cell_line = (circumcircle.getX() + (500.0 * sign));			
			double y_cell_line = perp_delta * (x_cell_line - circumcircle.getX()) + circumcircle.getY();

			Point3D[] result = new Point3D[2];
			result[0] = circumcircle;
			result[1] = new Point3D(x_cell_line, y_cell_line);

			return result;
		}
	}

	/**
	 * returns an iterator object involved in the last update.
	 * @return iterator to all triangles involved in the last update of the
	 *         triangulation NOTE: works ONLY if the are triangles (it there is
	 *         only a half plane - returns an empty iterator
	 */
	/*public Iterator<Triangle> getLastUpdatedTriangles() {
		List<Triangle> tmp = new ArrayList<Triangle>();
		if (this.trianglesSize() > 1) {
			Triangle t = currT;
			allTriangles(t, tmp, this.modCount);
		}
		return tmp.iterator();
	}*/

	/*private void allTriangles(Triangle curr, List<Triangle> front, int mc) {
		if (curr != null && curr.modCounter == mc && !front.contains(curr)) {
			front.add(curr);
			allTriangles(curr.abnext, front, mc);
			allTriangles(curr.bcnext, front, mc);
			allTriangles(curr.canext, front, mc);
		}
	}*/

	private Triangle insertPointSimple(Set<Point3D> vertices, Point3D p) {
		if (!allCollinear) {
			return insertNonColinear(p);
		} else {
			return insertColinear(vertices, p);
		}
	}

	private Triangle insertColinear(Set<Point3D> vertices, Point3D p) {
		if (vertices.size() == 1) {
			firstP = p;
		} else if (vertices.size() == 2) {
			startTriangulation(firstP, p);
		} else {
			testPoint(p);
		}
		return null;
	}

	private Triangle insertNonColinear(Point3D p) {
		Triangle t = find(startTriangle, p);
		if (t.halfplane)
			startTriangle = extendOutside(t, p);
		else
			startTriangle = extendInside(t, p);
		return startTriangle;
	}

	private void testPoint(Point3D p) {
		switch (PointLineTest.pointLineTest(firstP, lastP, p)) {
		case PointLineTest.LEFT:
			startTriangle = extendOutside(firstT.abnext, p);
			allCollinear = false;
			break;
		case PointLineTest.RIGHT:
			startTriangle = extendOutside(firstT, p);
			allCollinear = false;
			break;
		case PointLineTest.ONSEGMENT:
			insertCollinear(p, PointLineTest.ONSEGMENT);
			break;
		case PointLineTest.INFRONTOFA:
			insertCollinear(p, PointLineTest.INFRONTOFA);
			break;
		case PointLineTest.BEHINDB:
			insertCollinear(p, PointLineTest.BEHINDB);
			break;
		}
	}

	private void insertCollinear(Point3D p, int res) {
		Triangle t, tp, u;

		switch (res) {
		case PointLineTest.INFRONTOFA:
			t = new Triangle(firstP, p);
			tp = new Triangle(p, firstP);
			t.abnext = tp;
			tp.abnext = t;
			t.bcnext = tp;
			tp.canext = t;
			t.canext = firstT;
			firstT.bcnext = t;
			tp.bcnext = firstT.abnext;
			firstT.abnext.canext = tp;
			firstT = t;
			firstP = p;
			break;
		case PointLineTest.BEHINDB:
			t = new Triangle(p, lastP);
			tp = new Triangle(lastP, p);
			t.abnext = tp;
			tp.abnext = t;
			t.bcnext = lastT;
			lastT.canext = t;
			t.canext = tp;
			tp.bcnext = t;
			tp.canext = lastT.abnext;
			lastT.abnext.bcnext = tp;
			lastT = t;
			lastP = p;
			break;
		case PointLineTest.ONSEGMENT:
			u = firstT;
			while (PointComparator.isGreater(p, u.a))
				u = u.canext;
			t = new Triangle(p, u.b);
			tp = new Triangle(u.b, p);
			u.b = p;
			u.abnext.a = p;
			t.abnext = tp;
			tp.abnext = t;
			t.bcnext = u.bcnext;
			u.bcnext.canext = t;
			t.canext = u;
			u.bcnext = t;
			tp.canext = u.abnext.canext;
			u.abnext.canext.bcnext = tp;
			tp.bcnext = u.abnext;
			u.abnext.canext = tp;
			if (firstT.equals(u)) {
				System.out.println("is equal triangle");
				firstT = t;
			}
			break;
		}
	}

	private void startTriangulation(Point3D p1, Point3D p2) {
		Point3D ps, pb;
		if (PointComparator.isLess(p1, p2)) {
			ps = p1;
			pb = p2;
		} else {
			ps = p2;
			pb = p1;
		}
		firstT = new Triangle(pb, ps);
		lastT = firstT;

		Triangle t = new Triangle(ps, pb);
		firstT.abnext = t;
		t.abnext = firstT;
		firstT.bcnext = t;
		t.canext = firstT;
		firstT.canext = t;
		t.bcnext = firstT;

		firstP = firstT.b;
		lastP = lastT.a;
		startTriangleHull = firstT;
	}

	private Triangle extendInside(Triangle t, Point3D p) {

		Triangle h1, h2;
		h1 = treatDegeneracyInside(t, p);
		if (h1 != null)
			return h1;

		h1 = new Triangle(t.c, t.a, p);
		h2 = new Triangle(t.b, t.c, p);
		t.c = p;
		t.circumcircle();
		h1.abnext = t.canext;
		h1.bcnext = t;
		h1.canext = h2;
		h2.abnext = t.bcnext;
		h2.bcnext = h1;
		h2.canext = t;
		h1.abnext.switchneighbors(t, h1);
		h2.abnext.switchneighbors(t, h2);
		t.bcnext = h2;
		t.canext = h1;
		return t;
	}

	private Triangle treatDegeneracyInside(Triangle t, Point3D p) {

		if (t.abnext.halfplane
				&& PointLineTest.pointLineTest(t.b, t.a, p) == PointLineTest.ONSEGMENT)
			return extendOutside(t.abnext, p);
		if (t.bcnext.halfplane
				&& PointLineTest.pointLineTest(t.c, t.b, p) == PointLineTest.ONSEGMENT)
			return extendOutside(t.bcnext, p);
		if (t.canext.halfplane
				&& PointLineTest.pointLineTest(t.a, t.c, p) == PointLineTest.ONSEGMENT)
			return extendOutside(t.canext, p);
		return null;
	}

	private Triangle extendOutside(Triangle t, Point3D p, int test) {
		if (PointLineTest.ONSEGMENT == test) {
			Triangle dg = new Triangle(t.a, t.b, p);
			Triangle hp = new Triangle(p, t.b);
			t.b = p;
			dg.abnext = t.abnext;
			dg.abnext.switchneighbors(t, dg);
			dg.bcnext = hp;
			hp.abnext = dg;
			dg.canext = t;
			t.abnext = dg;
			hp.bcnext = t.bcnext;
			hp.bcnext.canext = hp;
			hp.canext = t;
			t.bcnext = hp;
			return dg;
		}

		Triangle ccT = extendcounterclock(t, p);
		Triangle cT = extendclock(t, p);
		ccT.bcnext = cT;
		cT.canext = ccT;
		startTriangleHull = cT;
		return cT.abnext;
	}

	private Triangle extendOutside(Triangle t, Point3D p) {
		return extendOutside(t, p, PointLineTest.pointLineTest(t.a, t.b, p));
	}

	private Triangle extendcounterclock(Triangle t, Point3D p) {

		t.halfplane = false;
		t.c = p;
		t.circumcircle();

		Triangle tca = t.canext;

		if (PointLineTest.pointLineTest(tca.a, tca.b, p) >= PointLineTest.RIGHT) {
			Triangle nT = new Triangle(t.a, p);
			nT.abnext = t;
			t.canext = nT;
			nT.canext = tca;
			tca.bcnext = nT;
			return nT;
		}
		return extendcounterclock(tca, p);
	}

	private Triangle extendclock(Triangle t, Point3D p) {

		t.halfplane = false;
		t.c = p;
		t.circumcircle();

		Triangle tbc = t.bcnext;

		if (PointLineTest.pointLineTest(tbc.a, tbc.b, p) >= PointLineTest.RIGHT) {
			Triangle nT = new Triangle(p, t.b);
			nT.abnext = t;
			t.bcnext = nT;
			nT.bcnext = tbc;
			tbc.canext = nT;
			return nT;
		}
		return extendclock(tbc, p);
	}

	private void flip(Triangle t, int mc) {

		Triangle u = t.abnext, v;
		t.modCounter = mc;
		if (u.halfplane || !u.circumcircleContains(t.c)) {
			return;
		}

		if (t.a == u.a) {
			v = new Triangle(u.b, t.b, t.c);
			v.abnext = u.bcnext;
			t.abnext = u.abnext;
		} else if (t.a == u.b) {
			v = new Triangle(u.c, t.b, t.c);
			v.abnext = u.canext;
			t.abnext = u.bcnext;
		} else if (t.a == u.c) {
			v = new Triangle(u.a, t.b, t.c);
			v.abnext = u.abnext;
			t.abnext = u.canext;
		} else {
			throw new RuntimeException("Error in flip.");
		}

		v.modCounter = mc;
		v.bcnext = t.bcnext;
		v.abnext.switchneighbors(u, v);
		v.bcnext.switchneighbors(t, v);
		t.bcnext = v;
		v.canext = t;
		t.b = v.a;
		t.abnext.switchneighbors(u, t);
		t.circumcircle();

		//currT = v;
		flip(t, mc);
		flip(v, mc);
	}

	/**
	 * compute the number of vertices in the convex hull. <br />
	 * NOTE: has a 'bug-like' behavor: <br />
	 * in cases of colinear - not on a asix parallel rectangle,
	 * colinear points are reported
	 *
	 * @return the number of vertices in the convex hull.
	 */
	public int convexHullSize() {
		int ans = 0;
		Iterator<Point3D> it = this.getConvexHullVerticesIterator();
		while (it.hasNext()) {
			ans++;
			it.next();
		}
		return ans;
	}

	/**
	 * finds the triangle the query point falls in, note if out-side of this
	 * triangulation a half plane triangle will be returned (see contains), the
	 * search has expected time of O(n^0.5), and it starts form a fixed triangle
	 * (this.startTriangle),
	 *
	 * @param p
	 *            query point
	 * @return the triangle that point p is in.
	 */
	public Triangle find(Point3D p) {

		// If triangulation has a spatial index try to use it as the starting triangle
		Triangle searchTriangle = startTriangle;
		/*if(gridIndex != null) {
			Triangle indexTriangle = gridIndex.findCellTriangleOf(p);
			if(indexTriangle != null)
				searchTriangle = indexTriangle;
		}*/

		// Search for the point's triangle starting from searchTriangle
		return find(searchTriangle, p);
	}

	/**
	 * finds the triangle the query point falls in, note if out-side of this
	 * triangulation a half plane triangle will be returned (see contains). the
	 * search starts from the the start triangle
	 *
	 * @param p
	 *            query point
	 * @param start
	 *            the triangle the search starts at.
	 * @return the triangle that point p is in..
	 */
	public Triangle find(Point3D p, Triangle start) {
		if (start == null)
			start = this.startTriangle;
		Triangle T = find(start, p);
		return T;
	}


	private static Triangle find(Triangle curr, Point3D p) {
		if (p == null)
			return null;

		Triangle next_t;
		if (curr.halfplane) {
			next_t = findnext2(p, curr);
			if (next_t == null || next_t.halfplane)
				return curr;
			curr = next_t;
		}
		while (true) {
			next_t = findnext1(p, curr);
			if (next_t == null)
				return curr;
			if (next_t.halfplane)
				return next_t;
			curr = next_t;
		}
	}

	/*
	 * assumes v is NOT an halfplane!
	 * returns the next triangle for find.
	 */
	private static Triangle findnext1(Point3D p, Triangle v) {
		if (!v.abnext.halfplane && PointLineTest.pointLineTest(v.a, v.b, p) == PointLineTest.RIGHT)
			return v.abnext;
		if (!v.bcnext.halfplane && PointLineTest.pointLineTest(v.b, v.c, p) == PointLineTest.RIGHT)
			return v.bcnext;
		if (!v.canext.halfplane && PointLineTest.pointLineTest(v.c, v.a, p) == PointLineTest.RIGHT)
			return v.canext;
		if (PointLineTest.pointLineTest(v.a, v.b, p) == PointLineTest.RIGHT)
			return v.abnext;
		if (PointLineTest.pointLineTest(v.b, v.c, p) == PointLineTest.RIGHT)
			return v.bcnext;
		if (PointLineTest.pointLineTest(v.c, v.a, p) == PointLineTest.RIGHT)
			return v.canext;
		return null;
	}

	/** assumes v is an halfplane! - returns another (none halfplane) triangle */
	private static Triangle findnext2(Point3D p, Triangle v) {
		if (v.abnext != null && !v.abnext.halfplane)
			return v.abnext;
		if (v.bcnext != null && !v.bcnext.halfplane)
			return v.bcnext;
		if (v.canext != null && !v.canext.halfplane)
			return v.canext;
		return null;
	}

	public List<Point3D> findConnectedVertices(Point3D point, List<Triangle> triangles) {
		// Finding the triangles to delete.
		List<Triangle> deletedTriangles = findConnectedTriangles(point);

		Set<Point3D> pointsSet = new HashSet<Point3D>();
		List<Point3D> pointsVec = new ArrayList<Point3D>();

		if (deletedTriangles != null) {
			connectTriangles(point, pointsSet, pointsVec, triangles);
			return pointsVec;
		} else {
			System.err.println("findConnectedVertices: Could not find connected vertices since the first found triangle doesn't" +
					" share the given point.");
			return null;
		}
	}

	/*
	 * Receives a point and returns all the points of the triangles that
	 * shares point as a corner (Connected vertices to this point).
	 * 
	 * By Doron Ganel & Eyal Roth
	 */
	private List<Triangle> findConnectedTriangles(Point3D point) {

		// Getting one of the neigh
		Triangle triangle = find(point);

		// Validating find result.
		if (!triangle.isCorner(point)) {
			System.err.println("findConnectedTriangles: Could not find connected vertices since the first found triangle doesn't" +
					" share the given point.");
			return null;
		}

		return findTriangleNeighborhood(triangle, point);
	}

	private void connectTriangles(Point3D point, Set<Point3D> pointsSet,
			List<Point3D> pointsVec, List<Triangle> triangles) {
		for (Triangle tmpTriangle : triangles) {
			Point3D point1 = tmpTriangle.p1();
			Point3D point2 = tmpTriangle.p2();
			Point3D point3 = tmpTriangle.p3();

			if (point1.equals(point) && !pointsSet.contains(point2)) {
				pointsSet.add(point2);
				pointsVec.add(point2);
			}

			if (point2.equals(point) && !pointsSet.contains(point3)) {
				pointsSet.add(point3);
				pointsVec.add(point3);
			}

			if (point3.equals(point)&& !pointsSet.contains(point1)) {
				pointsSet.add(point1);
				pointsVec.add(point1);
			}
		}
	}

	/*private boolean onPerimeter(List<Triangle> triangles) {
		for(Triangle t : triangles) {
			if(t.isHalfplane())
				return true;
		}
		return false;
	}*/

	// Walks on a consistent side of triangles until a cycle is achieved.
	//By Doron Ganel & Eyal Roth
	// changed to public by Udi
	public List<Triangle> findTriangleNeighborhood(Triangle firstTriangle, Point3D point) {
		List<Triangle> triangles = new ArrayList<Triangle>(30);
		triangles.add(firstTriangle);

		Triangle prevTriangle = null;
		Triangle currentTriangle = firstTriangle;
		Triangle nextTriangle = currentTriangle.nextNeighbor(point, prevTriangle);

		while (!nextTriangle.equals(firstTriangle)) {
			//the point is on the perimeter
			if(nextTriangle.isHalfplane()) {
				return null;
			}

			triangles.add(nextTriangle);
			prevTriangle = currentTriangle;
			currentTriangle = nextTriangle;
			nextTriangle = currentTriangle.nextNeighbor(point, prevTriangle);
		}

		return triangles;
	}	

	/*
	 * find triangle to be added to the triangulation
	 * 
	 * By: Doron Ganel & Eyal Roth
	 * 
	 */
	/*private Triangle findTriangle(List<Point3D> pointsVec, Point3D p) {
		Point3D[] arrayPoints = new Point3D[pointsVec.size()];
		pointsVec.toArray(arrayPoints);

		int size = arrayPoints.length;
		if(size < 3) {
			return null;
		}
		// if we left with 3 points we return the triangle
		else if(size==3) {
			return new Triangle(arrayPoints[0],arrayPoints[1],arrayPoints[2]);
		}
		else {
			for(int i=0;i<=size-1;i++) {
				Point3D p1 = arrayPoints[i];
				int j = i+1;
				int k = i+2;
				if(j >= size) {
					j = 0;
					k = 1;
				}
				//check IndexOutOfBound
				else if(k >= size) {
					k = 0;
				}

				Point3D p2 = arrayPoints[j];
				Point3D p3 = arrayPoints[k];

				//check if the triangle is not re-entrant and not encloses p
				Triangle t = new Triangle(p1,p2,p3);
				if (t.calcDet() >= 0) {
					if (!t.contains(p)||((size == 4) && !t.containsBoundaryIsOutside(p))) {                                                       
						if(!t.fallInsideCircumcircle(arrayPoints)) {
							return t;
						}
					}
				}
			}
		}

		return null;
	}*/

	/**
	 *
	 * @param p
	 *            query point
	 * @return true iff p is within this triangulation (in its 2D convex hull).
	 */

	public boolean contains(Point3D p) {
		Triangle tt = find(p);
		return !tt.halfplane;
	}

	/**
	 *
	 * @param x
	 *            - X cordination of the query point
	 * @param y
	 *            - Y cordination of the query point
	 * @return true iff (x,y) falls inside this triangulation (in its 2D convex
	 *         hull).
	 */
	public boolean contains(double x, double y) {
		return contains(new Point3D(x, y));
	}

	/**
	 *
	 * @param q
	 *            Query point
	 * @return the q point with updated Z value (z value is as given the
	 *         triangulation).
	 */
	public Point3D z(Point3D q) {
		Triangle t = find(q);
		return t.z(q);
	}

	/**
	 *
	 * @param x
	 *            - X cordination of the query point
	 * @param y
	 *            - Y cordination of the query point
	 * @return the q point with updated Z value (z value is as given the
	 *         triangulation).
	 */
	public double z(double x, double y) {
		Point3D q = new Point3D(x, y);
		Triangle t = find(q);
		return t.z_value(q);
	}

	private void updateBoundingBox(Point3D p) {
		double x = p.getX(), y = p.getY(), z = p.getZ();

		if (x < bbMin.getX()) {
			bbMin.setX(x);
		} else if (x > bbMax.getX()) {
			bbMax.setX(x);
		}
		if (y < bbMin.getY()) {
			bbMin.setY(y);
		} else if (y > bbMax.getY()) {
			bbMax.setY(y);
		}
		if (z < bbMin.getZ()) {
			bbMin.setZ(z);
		} else if (z > bbMax.getZ()) {
			bbMax.setZ(z);
		}
	}
	/**
	 * @return  The bounding rectange between the minimum and maximum coordinates
	 */
	public BoundingBox getBoundingBox() {
		return new BoundingBox(bbMin, bbMax);
	}

	/**
	 * return the min point of the bounding box of this triangulation
	 * {{x0,y0,z0}}
	 */
	public Point3D minBoundingBox() {
		return bbMin;
	}

	/**
	 * return the max point of the bounding box of this triangulation
	 * {{x1,y1,z1}}
	 */
	public Point3D maxBoundingBox() {
		return bbMax;
	}

	/**
	 * computes the current set (vector) of all triangles and
	 * return an iterator to them.
	 *
	 * @return an iterator to the current set of all triangles.
	 */
	/*public Iterator<Triangle> trianglesIterator() {
		if (this.size() <= 2)
			triangles = new ArrayList<Triangle>();
		initTriangles();
		return triangles.iterator();
	}*/

	/**
	 * returns an iterator to the set of all the points on the XY-convex hull
	 * @return iterator to the set of all the points on the XY-convex hull.
	 */
	private Iterator<Point3D> getConvexHullVerticesIterator() {
		List<Point3D> ans = new ArrayList<Point3D>();
		Triangle curr = this.startTriangleHull;
		boolean cont = true;
		double x0 = bbMin.getX(), x1 = bbMax.getX();
		double y0 = bbMin.getY(), y1 = bbMax.getY();
		boolean sx, sy;
		while (cont) {
			sx = curr.p1().getX() == x0 || curr.p1().getX() == x1;
			sy = curr.p1().getY() == y0 || curr.p1().getY() == y1;
			if ((sx && sy) || (!sx && !sy)) {
				ans.add(curr.p1());
			}
			if (curr.bcnext != null && curr.bcnext.halfplane)
				curr = curr.bcnext;
			if (curr == this.startTriangleHull)
				cont = false;
		}
		return ans.iterator();
	}

	private List<Triangle> generateTriangles() {

		List<Triangle> front = new ArrayList<Triangle>();

		List<Triangle> triangles = new ArrayList<Triangle>();
		front.add(this.startTriangle);

		Set<Triangle> triSet = new HashSet<Triangle>();

		while (front.size() > 0) {
			Triangle t = front.remove(0);
			if (t.mark == false) {
				t.mark = true;

				triangles.add(t);
				triSet.add(t);

				checkToInclude(t, t.abnext, front);
				checkToInclude(t, t.bcnext, front);
				checkToInclude(t, t.canext, front);
			}
		}

		return triangles;
	}

	private void checkToInclude(Triangle t, Triangle nextTriangle, List<Triangle> front) {
		if (nextTriangle != null && !nextTriangle.mark && !nextTriangle.isHalfplane()) {
			front.add(nextTriangle);
		}
	}

	/**
	 * Index the triangulation using a grid index
	 *  @param   xCellCount        number of grid cells in a row
	 *  @param   yCellCount        number of grid cells in a column
	 */
	/*public void indexData(int xCellCount, int yCellCount) {
		gridIndex = new GridIndex(this, xCellCount, yCellCount);
	}*/

	/**
	 * Remove any existing spatial indexing
	 */
	/*public void removeIndex() {
		gridIndex = null;
	}*/

	/**
	 * Triangulate given points. 
	 * Note: duplicated points are ignored.
	 * @param points
	 * @return list of triangles
	 */
	public List<Triangle> triangulate(List<Point3D> points) {
		init(points.size());

		Set<Point3D> vertices = new TreeSet<Point3D>(new PointComparator());

		bbMin = new Point3D(points.get(0));
		bbMax = new Point3D(points.get(0));

		//Insert Points
		for (Point3D point:points) {
			this.insertPoint(vertices, point);
		}

		List<Triangle> triangles = null;

		if (modCount != modCount2 && vertices.size() > 2) {
			triangles = generateTriangles();
		}

		return triangles;
	}
}