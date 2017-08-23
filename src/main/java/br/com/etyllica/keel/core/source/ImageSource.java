package br.com.etyllica.keel.core.source;

public interface ImageSource {

    int getWidth();

    int getHeight();

    int getRGB(int x, int y);
}
