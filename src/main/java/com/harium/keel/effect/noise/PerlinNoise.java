package com.harium.keel.effect.noise;

import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.util.HeightMapUtil;
import org.spongepowered.noise.NoiseQuality;
import org.spongepowered.noise.module.source.Perlin;

public class PerlinNoise implements Effect {

  private static final double MIN_SCALE = 0.00001;

  int seed = 12345;
  double scale = 1;

  int octaves = 3;
  double frequency = 0.04;
  double lacunarity = 2;
  double persistance = 0.5;

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
    Perlin perlin = new Perlin();
    perlin.setFrequency(frequency);
    perlin.setLacunarity(lacunarity);
    perlin.setNoiseQuality(NoiseQuality.STANDARD);
    perlin.setPersistence(persistance);
    perlin.setOctaveCount(octaves);
    perlin.setSeed(seed);

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
        double v = perlin.getValue(sampleX, sampleY, z);
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

  public PerlinNoise seed(int seed) {
    this.seed = seed;
    return this;
  }

  public double scale() {
    return scale;
  }

  public PerlinNoise scale(double scale) {
    this.scale = scale;
    if (this.scale <= 0) {
      this.scale = MIN_SCALE;
    }
    return this;
  }

  public int octaves() {
    return octaves;
  }

  public PerlinNoise octaves(int octaves) {
    this.octaves = octaves;
    return this;
  }

  public double frequency() {
    return frequency;
  }

  public PerlinNoise frequency(double frequency) {
    this.frequency = frequency;
    return this;
  }

  public double lacunarity() {
    return lacunarity;
  }

  public PerlinNoise lacunarity(double lacunarity) {
    this.lacunarity = lacunarity;
    return this;
  }

  public double persistance() {
    return persistance;
  }

  public PerlinNoise persistance(double persistance) {
    this.persistance = persistance;
    return this;
  }

}
