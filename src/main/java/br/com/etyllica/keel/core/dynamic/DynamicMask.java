package br.com.etyllica.keel.core.dynamic;


public interface DynamicMask {

	public int getW();

	public int getH();
	
	public void reset();
	
	public boolean isUnknown(int px, int py);
	
	public boolean isValid(int px, int py);
	
	public boolean isTouched(int px, int py);
		
	public void setValid(int px, int py);
	
	public void setInvalid(int px, int py);
	
	public void setTouched(int px, int py);

}
