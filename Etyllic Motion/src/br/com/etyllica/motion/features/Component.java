package br.com.etyllica.motion.features;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.layer.Layer;
import br.com.etyllica.linear.Point2D;

public class Component extends ColorComponent implements Comparable<Component>{

	protected List<Point2D> points = new ArrayList<Point2D>();
	
	protected int lowestX = 640;
	protected int lowestY = 480;
	protected int highestX = 0;
	protected int highestY = 0;
			
	public Component(int w, int h){
		super();
		
		highestX = 0;
		highestY = 0;
		
		lowestX = w;
		lowestY = h;		
	}
	
	public void add(int x, int y){
		add(new Point2D(x, y));
	}
	
	public void add(Point2D p, int rgb){
		byte r = (byte) ((rgb & 0x00ff0000) >> 16);
		byte g = (byte) ((rgb & 0x0000ff00) >> 8);
		byte b = (byte) (rgb & 0x000000ff);
		
		addLogic(p);
	}
		
	public void add(Point2D p){
		
		if((int)p.getX()>highestX){
			highestX = (int)p.getX();
		}
		else if((int)p.getX()<lowestX){
			lowestX = (int)p.getX();
		}
		
		if((int)p.getY()>highestY){
			highestY = (int)p.getY();
		}
		else if((int)p.getY()<lowestY){
			lowestY = (int)p.getY();
		}
		
		points.add(p);
		addLogic(p);
	}
	
	protected void addLogic(Point2D p){
		
	}
	
	public int getNumeroPontos(){
		return points.size();
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

	public int getW(){
		return highestX-lowestX;
	}
	public int getH(){
		return highestY-lowestY;
	}
	
	public Polygon getBoundingBox(){
		Polygon p = new Polygon();
		p.addPoint(lowestX,lowestY);
		p.addPoint(highestX,lowestY);
		p.addPoint(highestX,highestY);
		p.addPoint(lowestX,highestY);
		
		return p;
	}
	
	public double getConcentration(){
		int area = getW()*getH();
		
		return (double)(area/points.size());
		
	}

	public Layer getCamada() {
		
		return new Layer(lowestX,lowestY,getW(),getH());
	}

	public List<Point2D> getPoints() {
		return points;
	}

	@Override
	public int compareTo(Component component) {
				
		// TODO Auto-generated method stub
		//return component.getPoints().size()*getH()-points.size()*getH();
		
		double dif = component.getConcentration()*component.getH()-this.getConcentration()*getH();
		
		if(dif>0){
			return 1;
		}else if(dif<0){
			return -1;
		}else{
			return 0;
		}
			
	}
		
	public void merge(Component component){
		
		for(Point2D point:component.getPoints()){
			add(point);
		}
		
	}
	
	public void mergePolygon(Polygon p){
		
		for(int i=0;i<p.npoints;i++){
			
			Point2D point = new Point2D(p.xpoints[i], p.ypoints[i]);
			
			add(point);
		}
	}

}
