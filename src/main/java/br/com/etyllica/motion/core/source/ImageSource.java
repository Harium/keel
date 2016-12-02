package br.com.etyllica.motion.core.source;

public interface ImageSource {
  
  public int getWidth();
  public int getHeight();
  public int getRGB(int x, int y);
  
}
