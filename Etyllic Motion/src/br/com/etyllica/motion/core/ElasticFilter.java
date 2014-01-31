package br.com.etyllica.motion.core;

public abstract class ElasticFilter extends BooleanMaskFilter{
	
	protected int border = 1;
	
	protected int step = 1;
	
	public ElasticFilter(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}
	
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
}
