package br.com.etyllica.keel.core.gesture;

import java.awt.Polygon;
import java.util.List;

import br.com.etyllica.linear.Point2D;

public class PolygonMatcher {

	private int minDistance = 0;
	
	public String toRegExp(List<Point2D> points){
		
		StringBuilder builder = new StringBuilder();
	    
        double ox = points.get(0).getX();
        double oy = points.get(0).getY();
       
        for(int i=1;i<points.size();i++){
        	
        	double px = points.get(i).getX();
        	double py = points.get(i).getY();
        	           
        	builder.append(getExp(ox,oy,px,py));
           
            ox = px;
            oy = py;
           
        }
       
        return builder.toString();
		
	}
	
	public String toRegExp(Polygon p){
	       
        StringBuilder builder = new StringBuilder();
       
        double ox = p.xpoints[0];
        double oy = p.ypoints[0];
       
        for(int i=1;i<p.npoints;i++){
           
        	double px = p.xpoints[i];
        	double py = p.ypoints[i];
        	
        	builder.append(getExp(ox,oy,px,py));
           
            ox = px;
            oy = py;
           
        }
       
        return builder.toString();
       
    }
	
	private String getExp(double ox, double oy, double px, double py){
		
		Point2D p = new Point2D(ox, oy);
		
		if(p.distance(px,py)<minDistance){
    		return "";
    	}
		
		if(ox<=px){
            
            if(oy>=py){
                return "B";
            }else if(oy<py){
                return "D";
            }
           
        }else if(ox>px){
           
            if(oy>=py){
                return "A";
            }else if(oy<py){
                return "C";
            }
           
        }
		
		return "";
		
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}
		
}
