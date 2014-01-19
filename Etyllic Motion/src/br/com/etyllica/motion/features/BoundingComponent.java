package br.com.etyllica.motion.features;

public class BoundingComponent extends Component{

	public BoundingComponent(int w, int h){
		this(0,0,w,h);
	}
	
	public BoundingComponent(int x, int y, int w, int h){
		super(w,h);		

		lowestX = x;
		
		lowestY = y;
		
		highestX = w;
		
		highestY = h;
		
	}
	
	
	
}
