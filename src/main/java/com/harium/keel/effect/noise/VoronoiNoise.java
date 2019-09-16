package com.harium.keel.effect.noise;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.util.HeightMapUtil;
import org.spongepowered.noise.module.source.Voronoi;

public class VoronoiNoise implements Effect {

  private static final double MIN_SCALE = 0.00001;

  int seed = 12345;
  double scale = 1;

  double frequency = 0.04;
  double displacement = 1;
  boolean enableDisplacement = true;

  double x;
  double y;
  double z = 0;

  @Override
  public ImageSource apply(ImageSource feature) {
    int[][] values = build(feature);
    MatrixSource source = new MatrixSource(values);

    return source;
  }

  private int[][] build(ImageSource feature) {
    Voronoi voronoi = new Voronoi();
    voronoi.setFrequency(frequency);
    voronoi.setDisplacement(displacement);
    voronoi.setEnableDistance(enableDisplacement);
    voronoi.setSeed(seed);

    int width = feature.getWidth();
    int height = feature.getHeight();

    double min = Double.MAX_VALUE;
    double max = Double.MIN_VALUE;

    float halfWidth = width / 2f;
    float halfHeight = height / 2f;

    double[][] values = new double[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double sampleX = (x - halfWidth) / scale + this.x;
        double sampleY = (y - halfHeight) / scale + this.y;
        double v = voronoi.getValue(sampleX, sampleY, z);
        if (v < min) {
          min = v;
        }
        if (v > max) {
          max = v;
        }
        values[y][x] = v;
      }
    }

    return HeightMapUtil.convertToRGB(min, max, values);
  }

  public int seed() {
    return seed;
  }

  public VoronoiNoise seed(int seed) {
    this.seed = seed;
    return this;
  }

  public double scale() {
    return scale;
  }

  public VoronoiNoise scale(double scale) {
    this.scale = scale;
    if (this.scale <= 0) {
      this.scale = MIN_SCALE;
    }
    return this;
  }

  public double frequency() {
    return frequency;
  }

  public VoronoiNoise frequency(double frequency) {
    this.frequency = frequency;
    return this;
  }

  public double displacement() {
    return displacement;
  }

  public VoronoiNoise displacement(double displacement) {
    this.displacement = displacement;
    return this;
  }

  public boolean isEnableDisplacement() {
    return enableDisplacement;
  }

  public VoronoiNoise enableDisplacement(boolean enableDisplacement) {
    this.enableDisplacement = enableDisplacement;
    return this;
  }

}
