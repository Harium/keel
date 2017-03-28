package br.com.etyllica.keel.core.mask;


public interface DynamicMask {

	int getW();

	int getH();
	
	void reset();
	
	boolean isUnknown(int px, int py);
	
	boolean isValid(int px, int py);
	
	boolean isTouched(int px, int py);
		
	void setValid(int px, int py);
	
	void setInvalid(int px, int py);
	
	void setTouched(int px, int py);

}
