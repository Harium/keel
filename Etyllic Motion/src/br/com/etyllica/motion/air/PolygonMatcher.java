package br.com.etyllica.motion.air;

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
           
            if(ox<=points.get(i).getX()){
               
                if(oy>=points.get(i).getY()){
                    builder.append("B");
                }else if(oy<points.get(i).getY()){
                    builder.append("D");
                }
               
            }else if(ox>points.get(i).getX()){
               
                if(oy>=points.get(i).getY()){
                    builder.append("A");
                }else if(oy<points.get(i).getY()){
                    builder.append("C");
                }
               
            }
           
            ox = points.get(i).getX();
            oy = points.get(i).getY();
           
        }
       
        return builder.toString();
		
	}
	
	public String toRegExp(Polygon p){
	       
        StringBuilder builder = new StringBuilder();
       
        int ox = p.xpoints[0];
        int oy = p.ypoints[0];
       
        for(int i=1;i<p.npoints;i++){
           
            if(ox<=p.xpoints[i]){
               
                if(oy>=p.ypoints[i]){
                    builder.append("B");
                }else if(oy<p.ypoints[i]){
                    builder.append("D");
                }
               
            }else if(ox>p.xpoints[i]){
               
                if(oy>=p.ypoints[i]){
                    builder.append("A");
                }else if(oy<p.ypoints[i]){
                    builder.append("C");
                }
               
            }
           
            ox = p.xpoints[i];
            oy = p.ypoints[i];
           
        }
       
        return builder.toString();
       
    }

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}
		
}
