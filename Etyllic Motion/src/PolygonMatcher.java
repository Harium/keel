

import java.awt.Polygon;

public class PolygonMatcher {

    public static void main(String[] args) {

        Polygon p1 = new Polygon();
        p1.addPoint(10, 12);
        p1.addPoint(15, 10);
        p1.addPoint(14, 15);
       
        String exp1 = toRegExp(p1);
        
        System.out.println(exp1);
       
        Polygon p2 = new Polygon();
        p2.addPoint(10, 12);
        p2.addPoint(15, 5);
        p2.addPoint(15, 20);
        
        String exp2 = toRegExp(p2);
        
        System.out.println(exp2);

    }
   
    private static String toRegExp(Polygon p){
       
        StringBuilder builder = new StringBuilder();
       
        int ox = p.xpoints[0];
        int oy = p.ypoints[0];
       
        for(int i=1;i<p.npoints;i++){
           
            if(ox<=p.xpoints[i]){
               
                if(oy>p.ypoints[i]){
                    builder.append("A");
                }else if(oy<p.ypoints[i]){
                    builder.append("C");
                }else{
                    builder.append("(A|C)");
                }
               
            }else if(ox>p.xpoints[i]){
               
                if(oy>p.ypoints[i]){
                    builder.append("B");
                }else if(oy<p.ypoints[i]){
                    builder.append("D");
                }else{
                    builder.append("(B|D)");
                }
               
            }
           
            ox = p.xpoints[i];
            oy = p.ypoints[i];
           
        }
       
        return builder.toString();
       
    }

}
