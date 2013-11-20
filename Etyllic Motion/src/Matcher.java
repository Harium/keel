

import java.awt.Polygon;

import br.com.etyllica.motion.air.PolygonMatcher;

public class Matcher {

    public static void main(String[] args) {
    	
    	PolygonMatcher matcher = new PolygonMatcher();
    	
        Polygon p1 = new Polygon();
        p1.addPoint(10, 12);
        p1.addPoint(15, 10);
        p1.addPoint(14, 15);
       
        String exp1 = matcher.toRegExp(p1);
        
        System.out.println(exp1);
       
        Polygon p2 = new Polygon();
        p2.addPoint(10, 12);
        p2.addPoint(15, 5);
        p2.addPoint(15, 20);
        
        String exp2 = matcher.toRegExp(p2);
        
        System.out.println(exp2);

        //3 = BDCDCA
        
        String threeRegex = "B+.*D+.*C+.*D+.*C+.*A+.*";
        
    }
   
}
