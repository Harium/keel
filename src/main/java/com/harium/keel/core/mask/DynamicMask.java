package com.harium.keel.core.mask;


import com.harium.keel.core.source.ImageSource;

public interface DynamicMask {

	int getW();

	int getH();

	void init(int w, int h);
	void update(ImageSource source);

	boolean isUnknown(int px, int py);
	
	boolean isValid(int px, int py);
	
	boolean isTouched(int px, int py);
		
	void setValid(int px, int py);
	
	void setInvalid(int px, int py);
	
	void setTouched(int px, int py);

}
