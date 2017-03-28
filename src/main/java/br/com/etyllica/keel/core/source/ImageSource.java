package br.com.etyllica.keel.core.source;

public interface ImageSource {
  
  public int getWidth();
  public int getHeight();
  public int getRGB(int x, int y);
  
}
