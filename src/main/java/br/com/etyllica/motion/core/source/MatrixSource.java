package br.com.etyllica.motion.core.source;

public class MatrixSource implements ImageSource {
  
  private int w = 0;
  private int h = 0;
  
  private int[][] matrix;
  
  public MatrixSource(int w, int h) {
    super();
    this.w = w;
    this.h = h;
    
    matrix = new int[w][h];
  }
  
  public int[][] getMatrix() {
    return matrix;
  }
  
  public int getRGB(int x, int y) {
    return matrix[x][y];
  }

  @Override
  public int getW(int x, int y) {
    return w;
  }

  @Override
  public int getH(int x, int y) {
    return h;
  }
  
}
