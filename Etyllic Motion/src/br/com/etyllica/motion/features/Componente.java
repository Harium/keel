package br.com.etyllica.motion.features;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.layer.Layer;
import br.com.etyllica.linear.Ponto2D;

public class Componente implements Comparable<Componente>{

	private List<Ponto2D> points;
	
	private int menorX = 640;
	private int menorY = 480;
	private int maiorX = 0;
	private int maiorY = 0;
	
	private long mediaR = 0;
	private long mediaG = 0;
	private long mediaB = 0;
	
	public Componente(int w, int h){
		
		menorX = w;
		menorY = h;
		
		points = new ArrayList<Ponto2D>();
	}
	
	public void add(Ponto2D p, int rgb){
		byte r = (byte) ((rgb & 0x00ff0000) >> 16);
		byte g = (byte) ((rgb & 0x0000ff00) >> 8);
		byte b = (byte) (rgb & 0x000000ff);
		
		addLogic(p);
	}
		
	public void add(Ponto2D p){
		
		if((int)p.getX()>maiorX){
			maiorX = (int)p.getX();
		}
		else if((int)p.getX()<menorX){
			menorX = (int)p.getX();
		}
		
		if((int)p.getY()>maiorY){
			maiorY = (int)p.getY();
		}
		else if((int)p.getY()<menorY){
			menorY = (int)p.getY();
		}
		
		points.add(p);
		addLogic(p);
	}
	
	protected void addLogic(Ponto2D p){
		
	}
	
	public int getNumeroPontos(){
		return points.size();
	}
		
	public int getMenorX(){
		return menorX;
	}
	public int getMaiorX(){
		return maiorX;
	}
	public int getMenorY(){
		return menorY;
	}
	public int getMaiorY(){
		return maiorY;
	}
	
	public int getW(){
		return maiorX-menorX;
	}
	public int getH(){
		return maiorY-menorY;
	}
	
	public Polygon getBoundingBox(){
		Polygon p = new Polygon();
		p.addPoint(menorX,menorY);
		p.addPoint(maiorX,menorY);
		p.addPoint(maiorX,maiorY);
		p.addPoint(menorX,maiorY);
		
		return p;
	}

	public Layer getCamada() {
		
		return new Layer(menorX,menorY,getW(),getH());
	}

	public List<Ponto2D> getPoints() {
		return points;
	}

	@Override
	public int compareTo(Componente component) {
		// TODO Auto-generated method stub
		return component.getPoints().size()-points.size();
	}
			
}
