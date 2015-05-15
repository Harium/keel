package br.com.etyllica.motion.source;

public interface ImageSource {
  
  public int getW(int x, int y);
  public int getH(int x, int y);
  public int getRGB(int x, int y);
  
}
