package br.com.etyllica.motion.core.features;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.layer.GeometricLayer;
import br.com.etyllica.layer.Layer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.filter.color.ColorStrategy;

public class Component extends ColorComponent implements Comparable<Component> {

	protected List<Point2D> points = new ArrayList<Point2D>();

	protected int lowestX = 640;

	protected int lowestY = 480;

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

	public void add(int x, int y) {
		add(new Point2D(x, y));
	}

	public void add(Point2D p, int rgb) {
		int r = ColorStrategy.getRed(rgb);
		int g = ColorStrategy.getGreen(rgb);
		int b = ColorStrategy.getBlue(rgb);

		addLogic(p);
	}

	public void add(Point2D p) {

		if((int)p.getX()>highestX) {
			highestX = (int)p.getX();
		}
		else if((int)p.getX()<lowestX) {
			lowestX = (int)p.getX();
		}

		if((int)p.getY()>highestY) {
			highestY = (int)p.getY();
		}
		else if((int)p.getY()<lowestY) {
			lowestY = (int)p.getY();
		}

		points.add(p);
		addLogic(p);
	}

	protected void addLogic(Point2D p) {

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
		p.addPoint(lowestX,lowestY);
		p.addPoint(highestX,lowestY);
		p.addPoint(highestX,highestY);
		p.addPoint(lowestX,highestY);

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
		int area = getArea();

		return (double)(area/points.size());

	}
	
	public int getArea() {
		return getW()*getH();
	}

	public Point2D getCenter() {

		double countX = 0;

		double countY = 0;

		for(Point2D point: points) {

			countX+=point.getX();

			countY+=point.getY();

		}

		Point2D center = new Point2D(countX/points.size(), countY/points.size());

		return center;
	}

	public Layer getCamada() {

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

		// TODO Auto-generated method stub
		//return component.getPoints().size()*getH()-points.size()*getH();

		double dif = component.getDensity()*component.getH()-this.getDensity()*getH();

		if(dif>0) {
			return 1;
		}else if(dif<0) {
			return -1;
		}else{
			return 0;
		}

	}

	public void merge(Component component) {

		for(Point2D point:component.getPoints()) {
			add(point);
		}

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

}
