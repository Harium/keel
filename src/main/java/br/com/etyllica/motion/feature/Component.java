package br.com.etyllica.motion.feature;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.layer.GeometricLayer;
import br.com.etyllica.layer.Layer;
import br.com.etyllica.linear.Point2D;

public class Component extends ColorComponent implements Feature, Comparable<Component> {

	private Point2D center = null;
	
	protected List<Point2D> points = new ArrayList<Point2D>();

	protected int lowestX = Integer.MAX_VALUE;
	protected int lowestY = Integer.MAX_VALUE;

	protected int highestX = 0;
	protected int highestY = 0;

	public Component() {
		this(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public Component(int w, int h) {
		super();

		highestX = 0;
		highestY = 0;

		lowestX = w;
		lowestY = h;
	}

	public Component(int x, int y, int w, int h) {
		super();

		lowestX = x;
		lowestY = y;

		highestX = w;
		highestY = h;

	}

	public boolean[][] generateMask() {

		int w = getW();
		int h = getH();
		
		if(w<0) {
			w = -w;
		}
		
		if(h<0) {
			h = -h;
		}
		
		boolean[][] mask = new boolean[h][w];

		for(Point2D point: points) {
						
			int x = (int)point.getX()-this.getLowestX();
			int y = (int)point.getY()-this.getLowestY();
			
			if(x<0 || y<0 || x>=w || y>=h)
				continue;
			
			mask[y][x] = true;
		}
		
		return mask;

	}

	public void add(int x, int y) {
		add(new Point2D(x, y));
	}

	public void add(Point2D p) {

		int px = (int)p.getX();
		int py = (int)p.getY();

		if(px > highestX) {
			highestX = px;
		} 
		
		if(px < lowestX) {
			lowestX = px;
		}

		if(py > highestY) {
			highestY = py;

		}
		
		if(py < lowestY) {
			lowestY = py;
		}

		center = null;
		addLogic(p);
	}
	

	public void addAll(List<Point2D> list) {
		for(Point2D point: list) {
			add(point);
		}
	}

	protected void addLogic(Point2D p) {
		points.add(p);
	}

	public int getPointCount() {
		return points.size();
	}

	public void setBounds(int lowestX, int lowestY, int width, int height) {
		this.lowestX = lowestX;
		this.lowestY = lowestY;

		this.highestX = lowestX+width;
		this.highestY = lowestY+height;
	}

	public int getLowestX() {
		return lowestX;
	}

	public void setLowestX(int lowestX) {
		this.lowestX = lowestX;
	}

	public int getLowestY() {
		return lowestY;
	}

	public void setLowestY(int lowestY) {
		this.lowestY = lowestY;
	}

	public int getHighestX() {
		return highestX;
	}

	public void setHighestX(int highestX) {
		this.highestX = highestX;
	}

	public int getHighestY() {
		return highestY;
	}

	public void setHighestY(int highestY) {
		this.highestY = highestY;
	}

	public Polygon getBoundingBox() {

		Polygon p = new Polygon();

		p.addPoint(lowestX, lowestY);
		p.addPoint(highestX,lowestY);
		p.addPoint(highestX, highestY);
		p.addPoint(lowestX, highestY);

		return p;
	}

	public Polygon getPolygon() {

		Polygon p = new Polygon();

		for(Point2D point: points) {
			p.addPoint((int)point.getX(), (int)point.getY());
		}

		return p;
	}

	public GeometricLayer getRectangle() {
		GeometricLayer rect = new GeometricLayer(lowestX, lowestY, highestX-lowestX, highestY-lowestY);
		return rect;
	}

	public double getDensity() {
		double area = getArea();

		if(area == 0) {
			return 1;
		}

		return ((double)points.size()*100/area);
	}

	public int getArea() {
		return getW()*getH();
	}

	public Point2D getCenter() {
		if(center!=null) {
			return center;
		}

		double countX = 0;
		double countY = 0;

		for(Point2D point: points) {
			countX+=point.getX();
			countY+=point.getY();
		}

		center = new Point2D(countX/points.size(), countY/points.size());

		return center;
	}

	public Layer getLayer() {
		return new Layer(lowestX,lowestY,getW(),getH());
	}

	public List<Point2D> getPoints() {
		return points;
	}

	public void setPoints(List<Point2D> points) {
		this.points = points;
	}

	@Override
	public int compareTo(Component component) {

		//return component.getPoints().size()*getW()-points.size()*getH();

		double dif = component.getDensity()*component.getH()-this.getDensity()*getW();

		if(dif>0) {
			return 1;
		}else if(dif<0) {
			return -1;
		}else{
			return 0;
		}

	}

	public void merge(Component component) {

		for(Point2D point:component.points) {
			add(point);
		}

	}

	public boolean colidePoint(int px, int py) {

		if((px<getX())||(px>getX() + getW())){
			return false;
		}

		if((py<getY())||(py>getY() + getH())){
			return false;
		}

		return true;
	}

	public boolean colide(Component component) {

		int bx = component.getX();
		int bw = component.getW();

		int by = component.getY();
		int bh = component.getH();

		if(bx + bw < getX())	return false;
		if(bx > getX() + getW())	return false;

		if(by + bh < getY())	return false;
		if(by > getY() + getH())	return false;

		return true;

	}

	public void mergePolygon(Polygon p) {

		for(int i=0;i<p.npoints;i++) {

			Point2D point = new Point2D(p.xpoints[i], p.ypoints[i]);

			add(point);
		}
	}

	public void setLocation(int x, int y) {

		this.lowestX = x;

		this.lowestY = y;

		this.highestX = x+1;

		this.highestY = y+1;

	}

	public int getX() {
		return lowestX;
	}

	public int getY() {
		return lowestY;
	}

	public int getW() {
		return highestX-lowestX;
	}

	public int getH() {
		return highestY-lowestY;
	}

	public void reset() {

		points.clear();

		highestX = 0;
		highestY = 0;

		lowestX = Integer.MAX_VALUE;
		lowestY = Integer.MAX_VALUE;

	}

	@Override
	public boolean isInside(int x, int y) {
		return true;
	}

	public boolean intersects(int x, int y) {
		return (x>=getX()&&x<getW()&&y>=getY()&&y<getH());
	}

}