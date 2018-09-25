// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
//    This library is free software; you can redistribute it and/or
//    modify it under the terms of the GNU Lesser General Public
//    License as published by the Free Software Foundation; either
//    version 2.1 of the License, or (at your option) any later version.
//
//    This library is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//    Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with this library; if not, write to the Free Software
//    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
//

package com.harium.keel.effect.artistic;


import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.effect.Desaturation;
import com.harium.keel.effect.GaussianBoxBlur;
import com.harium.keel.effect.Invert;

/**
 * Fake HDR.
 * Simulate HDR (High Dynamic Range) from single image.
 *
 * @author Diego Catalano
 */
public class FakeHDR implements Effect {

    private double desaturation = 0;
    private int sigma = 40;
    private double p = 0.62;

    /**
     * Get desaturation value.
     *
     * @return Desaturation value.
     */
    public double desaturation() {
        return desaturation;
    }

    /**
     * Set desaturation value.
     *
     * @param desaturation Desaturation [0..1].
     */
    public FakeHDR desaturation(double desaturation) {
        this.desaturation = desaturation;
        return this;
    }

    /**
     * Get sigma.
     *
     * @return Sigma.
     */
    public int getSigma() {
        return sigma;
    }

    /**
     * Set sigma.
     *
     * @param sigma Sigma.
     */
    public void setSigma(int sigma) {
        this.sigma = sigma;
    }

    /**
     * Get percentage.
     *
     * @return Percentage.
     */
    public double getPercentage() {
        return p;
    }

    /**
     * Set percentage.
     *
     * @param p Percentage [0..1].
     */
    public void setPercentage(double p) {
        this.p = Math.max(0, Math.min(1, p));
    }

    /**
     * Initialize a new instance of the FakeHDR class.
     */
    public FakeHDR() {
    }

    /**
     * Initialize a new instance of the FakeHDR class.
     *
     * @param percentage Percentage [0..1].
     */
    public FakeHDR(double percentage) {
        setPercentage(percentage);
    }

    /**
     * Initialize a new instance of the FakeHDR class.
     *
     * @param desaturation Desaturation [0..1].
     * @param sigma        Gaussian value.
     * @param percentage   Percentage [0..1].
     */
    public FakeHDR(double desaturation, int sigma, double percentage) {
        this.desaturation = desaturation;
        this.sigma = sigma;
        this.p = percentage;
    }

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource layerA = new MatrixSource(input);
        MatrixSource layerB = new MatrixSource(input);

        Desaturation d = new Desaturation().saturationFactor(desaturation);
        d.apply(layerA);

        Invert in = new Invert();
        in.apply(layerA);

        GaussianBoxBlur fgb = new GaussianBoxBlur(sigma);
        fgb.apply(layerA);

        Blend blend = new Blend().overlay(layerB).algorithm(Blend.Algorithm.Overlay);
        blend.apply(layerA);

        // Reload
        layerB = new MatrixSource(input);

        blend.algorithm(Blend.Algorithm.LinearLight);
        blend.overlay(layerA);
        blend.apply(layerB);

        Opacity o = new Opacity().overlay(layerB).setPercentage(p);
        o.apply(input);

        return input;
    }
}