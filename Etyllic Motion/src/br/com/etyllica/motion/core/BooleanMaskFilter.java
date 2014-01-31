package br.com.etyllica.motion.core;



public abstract class BooleanMaskFilter extends ComponentFilter{

	protected boolean[][] mask;
	
	public BooleanMaskFilter(int w, int h){
		super(w,h);
		
		resetMask(w, h);
	}
	
	@Override
	public void setW(int w) {
		this.w = w;
		
		resetMask(w,h);
	}

	@Override
	public void setH(int h) {
		this.h = h;
		
		resetMask(w,h);
	}
	
	private void resetMask(int w, int h){
		
		mask = new boolean[w][h];
		
		resetMask();
		
	}
	
	protected void resetMask(){
		
		int w = mask.length;
		int h = mask[0].length;
		
		for(int j=0;j<h;j++){
			
			for(int i=0;i<w;i++){
				mask[i][j] = false;
			}
			
		}
		
	}

	public boolean[][] getMask() {
		return mask;
	}

	@Override
	public void setup(){
		
		super.setup();
		
		resetMask();
		
	}
	
}
