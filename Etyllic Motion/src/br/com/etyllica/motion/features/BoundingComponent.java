package br.com.etyllica.motion.features;

public class BoundingComponent extends Component{

	public BoundingComponent(int x, int y, int w, int h){
		super(w,h);		

		lowestX = x;
		
		lowestY = y;
		
		highestX = w;
		
		highestY = h;
		
	}
	
	
	
}
