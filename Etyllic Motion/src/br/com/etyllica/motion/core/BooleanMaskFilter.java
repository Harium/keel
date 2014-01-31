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
		
		updateMask(0, 0, w, h, false);
				
	}
	
	public void updateMask(int i, int j, int w, int h, boolean update){
		
		for(int nj=j; nj<j+h; nj++){
			
			for(int ni=i; ni<i+w; ni++){
				mask[ni][nj] = update;
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
