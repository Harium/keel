// Catalano Imaging Library
// The Catalano Framework
//
// Copyright © Diego Catalano, 2012-2016
// diego.catalano at live.com
//
//    This library is free software; you can redistribute it and/or
//    apply it under the terms of the GNU Lesser General Public
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
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

import static com.harium.keel.effect.helper.EffectHelper.*;

/**
 * Blend Filter.
 * Blend filter is used to determine how two layers are blended into each other.
 * Reference: http://en.wikipedia.org/wiki/Blend_modes
 *
 * @author Diego Catalano
 */
public class Blend implements Effect {

    /**
     * Blend algorithm.
     */
    public enum Algorithm {

        /**
         * It selects the maximum of each component from the foreground and background pixels.
         */
        Lighten,

        /**
         * Creates a pixel that retains the smallest components of the foreground and background pixels.
         */
        Darken,

        /**
         * Simply multiplies each component in the two layers.
         */
        Multiply,

        /**
         * Simply average each component in the two layers.
         */
        Average,

        /**
         * This blend mode simply adds pixel values of one layer with the other. In case of values above 1 (in the case of RGB), white is displayed.
         */
        Add,

        /**
         * This blend mode simply subtracts pixel values of one layer with the other. In case of negative values, black is displayed.
         */
        Subtract,

        /**
         * Difference subtracts the top layer from the bottom layer or the other way round, to always get a positive value.
         * Blending with black produces no change, as values for all colors are 0. (The RGB value for black is 0,0,0). Blending with white inverts the picture.
         */
        Difference,

        /**
         * Produces the opposite effect to Difference. Instead of making colors darker, it makes them brighter.
         */
        Negation,

        /**
         * The Screen blend mode inverts both layers, multiplies them, and then inverts that result.
         */
        Screen,

        /**
         * Exclusion blending mode inverts lower layers according to the brightness values in the active layer.
         * White inverts the composite pixels absolutely, black inverts them not at all, and the other brightness values invert them to some degree in between.
         */
        Exclusion,

        /**
         * Overlay combines Multiply and Screen blend modes.
         * The parts of the top layer where base layer is light become lighter, the parts where the base layer is dark become darker. An overlay with the same picture looks like an S-curve.
         */
        Overlay,

        /**
         * This is a softer version of Hard Light. Applying pure black or white does not result in pure black or white.
         */
        SoftLight,

        /**
         * Hard Light combines Multiply and Screen blend modes. Equivalent to Overlay, but with the bottom and top images swapped.
         */
        HardLight,

        /**
         * Color Dodge blend mode divides the bottom layer by the inverted top layer.
         */
        ColorDodge,

        /**
         * Color Burn mode divides the inverted bottom layer by the top layer, and then inverts the result.
         */
        ColorBurn,

        /**
         * Linear Light combines Linear Dodge and Linear Burn (rescaled so that neutral colors become middle gray).
         * Dodge applies to values of top layer lighter than middle gray, and burn to darker values.
         * The calculation simplifies to the sum of bottom layer and twice the top layer, subtract 1. The contrast decreases.
         */
        LinearLight,

        /**
         * Vivid Light combines Color Dodge and Color Burn (rescaled so that neutral colors become middle gray).
         * Dodge applies when values in the top layer are lighter than middle gray, and burn to darker values.
         * The middle gray is the neutral color. When color is lighter than this, this effectively moves the white point of the bottom
         * layer down by twice the difference; when it is darker, the black point is moved up by twice the difference. (The perceived contrast increases.)
         */
        VividLight,

        /**
         * Pin Light combines lighten and darken modes.
         */
        PinLight,

        /**
         * Reflect can be used for adding shiny objects or areas of light.
         */
        Reflect,

        /**
         * Phoenix mode.
         */
        Phoenix
    }

    ;
    private Algorithm algorithm;
    private ImageSource overlay;

    /**
     * Get Overlay image.
     *
     * @return Overlay image.
     */
    public ImageSource overlay() {
        return overlay;
    }

    /**
     * Set Overlay image.
     *
     * @param overlay Overlay image.
     */
    public Blend overlay(ImageSource overlay) {
        this.overlay = overlay;
        return this;
    }

    /**
     * Get Blend algorithm.
     *
     * @return Blend algorithm.
     */
    public Algorithm algorithm() {
        return algorithm;
    }

    /**
     * Set Blend algorithm.
     *
     * @param algorithm Blend algorithm.
     */
    public Blend algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    @Override
    public ImageSource apply(ImageSource input) {

        int size = input.getWidth() * input.getHeight();

        switch (algorithm) {
            case Lighten:
                for (int i = 0; i < size; i++) {
                    if (getRed(i, overlay) > getRed(i, input)) {
                        setRed(i, getRed(i, overlay), input);
                    }
                    if (getGreen(i, overlay) > getGreen(i, input)) {
                        setGreen(i, getGreen(i, overlay), input);
                    }
                    if (getBlue(i, overlay) > getBlue(i, input)) {
                        setBlue(i, getBlue(i, overlay), input);
                    }
                }
                break;
            case Darken:
                for (int i = 0; i < size; i++) {
                    if (getRed(i, overlay) < getRed(i, input)) {
                        setRed(i, getRed(i, overlay), input);
                    }
                    if (getGreen(i, overlay) < getGreen(i, input)) {
                        setGreen(i, getGreen(i, overlay), input);
                    }
                    if (getBlue(i, overlay) < getBlue(i, input)) {
                        setBlue(i, getBlue(i, overlay), input);
                    }
                }
                break;
            case Multiply:
                for (int i = 0; i < size; i++) {
                    int r = getRed(i, input) * getRed(i, overlay) / 255;
                    int g = getGreen(i, input) * getGreen(i, overlay) / 255;
                    int b = getBlue(i, input) * getBlue(i, overlay) / 255;
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Average:
                for (int i = 0; i < size; i++) {
                    int r = getRed(i, input) * getRed(i, overlay) / 2;
                    int g = getGreen(i, input) * getGreen(i, overlay) / 2;
                    int b = getBlue(i, input) * getBlue(i, overlay) / 2;
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Add:
                for (int i = 0; i < size; i++) {
                    int r = Math.min(getRed(i, input) + getRed(i, overlay), 255);
                    int g = Math.min(getGreen(i, input) + getGreen(i, overlay), 255);
                    int b = Math.min(getBlue(i, input) + getBlue(i, overlay), 255);
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Subtract:
                for (int i = 0; i < size; i++) {
                    int temp = getRed(i, input) + getRed(i, overlay);
                    if (temp < 255) {
                        setRed(i, 0, input);
                    } else {
                        setRed(i, temp - 255, input);
                    }

                    temp = getGreen(i, input) + getGreen(i, overlay);
                    if (temp < 255) {
                        setGreen(i, 0, input);
                    } else {
                        setGreen(i, temp - 255, input);
                    }

                    temp = getBlue(i, input) + getBlue(i, overlay);
                    if (temp < 255) {
                        setBlue(i, 0, input);
                    } else {
                        setBlue(i, temp - 255, input);
                    }
                }
                break;
            case Difference:
                for (int i = 0; i < size; i++) {
                    int r = Math.abs(getRed(i, input) - getRed(i, overlay));
                    int g = Math.abs(getGreen(i, input) - getGreen(i, overlay));
                    int b = Math.abs(getBlue(i, input) - getBlue(i, overlay));
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Negation:
                for (int i = 0; i < size; i++) {
                    int r = 255 - Math.abs(255 - getRed(i, input) - getRed(i, overlay));
                    int g = 255 - Math.abs(255 - getGreen(i, input) - getGreen(i, overlay));
                    int b = 255 - Math.abs(255 - getBlue(i, input) - getBlue(i, overlay));
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Screen:
                for (int i = 0; i < size; i++) {
                    int r = ((255 - (((255 - getRed(i, input)) * (255 - getRed(i, overlay))) >> 8)));
                    int g = ((255 - (((255 - getGreen(i, input)) * (255 - getGreen(i, overlay))) >> 8)));
                    int b = ((255 - (((255 - getBlue(i, input)) * (255 - getBlue(i, overlay))) >> 8)));
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Exclusion:
                for (int i = 0; i < size; i++) {
                    int r = ((getRed(i, input) + getRed(i, overlay) - 2 * getRed(i, input) * getRed(i, overlay) / 255));
                    int g = ((getGreen(i, input) + getGreen(i, overlay) - 2 * getGreen(i, input) * getGreen(i, overlay) / 255));
                    int b = ((getBlue(i, input) + getBlue(i, overlay) - 2 * getBlue(i, input) * getBlue(i, overlay) / 255));
                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
            case Overlay:
                for (int i = 0; i < size; i++) {
                    int temp;
                    if (getRed(i, overlay) < 128) {
                        temp = (2 * getRed(i, input) * getRed(i, overlay) / 255);
                        temp = Math.min(255, temp);
                        setRed(i, temp, input);
                    } else {
                        temp = (255 - 2 * (255 - getRed(i, input)) * (255 - getRed(i, overlay)) / 255);
                        temp = Math.min(255, temp);
                        setRed(i, temp, input);
                    }

                    if (getGreen(i, overlay) < 128) {
                        temp = (2 * getGreen(i, input) * getGreen(i, overlay) / 255);
                        temp = Math.min(255, temp);
                        setGreen(i, temp, input);
                    } else {
                        temp = (255 - 2 * (255 - getGreen(i, input)) * (255 - getGreen(i, overlay)) / 255);
                        temp = Math.min(255, temp);
                        setGreen(i, temp, input);
                    }

                    if (getBlue(i, overlay) < 128) {
                        temp = (2 * getBlue(i, input) * getBlue(i, overlay) / 255);
                        temp = Math.min(255, temp);
                        setBlue(i, temp, input);
                    } else {
                        temp = (255 - 2 * (255 - getBlue(i, input)) * (255 - getBlue(i, overlay)) / 255);
                        temp = Math.min(255, temp);
                        setBlue(i, temp, input);
                    }
                }
                break;
            case SoftLight:
                for (int i = 0; i < size; i++) {
                    int temp;
                    if (getRed(i, input) < 128) {
                        temp = (2 * getRed(i, overlay) * getRed(i, input) / 255);
                        temp = Math.min(255, temp);
                        setRed(i, temp, input);
                    } else {
                        temp = (255 - 2 * (255 - getRed(i, overlay)) * (255 - getRed(i, input)) / 255);
                        temp = Math.min(255, temp);
                        setRed(i, temp, input);
                    }

                    if (getGreen(i, input) < 128) {
                        temp = (2 * getGreen(i, overlay) * getGreen(i, input) / 255);
                        temp = Math.min(255, temp);
                        setGreen(i, temp, input);
                    } else {
                        temp = (255 - 2 * (255 - getGreen(i, overlay)) * (255 - getGreen(i, input)) / 255);
                        temp = Math.min(255, temp);
                        setGreen(i, temp, input);
                    }

                    if (getBlue(i, input) < 128) {
                        temp = (2 * getBlue(i, overlay) * getBlue(i, input) / 255);
                        temp = Math.min(255, temp);
                        setBlue(i, temp, input);
                    } else {
                        temp = (255 - 2 * (255 - getBlue(i, overlay)) * (255 - getBlue(i, input)) / 255);
                        temp = Math.min(255, temp);
                        setBlue(i, temp, input);
                    }
                }
                break;
            case HardLight:
                for (int i = 0; i < size; i++) {
                    float temp;
                    if (getRed(i, overlay) < 128) {
                        temp = (2 * ((getRed(i, input) >> 1) + 64)) * ((float) getRed(i, overlay) / 255);
                        setRed(i, (int) temp, input);
                    } else {
                        temp = (255 - (2 * (255 - ((getRed(i, input) >> 1) + 64)) * (float) (255 - getRed(i, overlay)) / 255));
                        setRed(i, (int) temp, input);
                    }

                    if (getGreen(i, overlay) < 128) {
                        temp = (2 * ((getGreen(i, input) >> 1) + 64)) * ((float) getGreen(i, overlay) / 255);
                        setGreen(i, (int) temp, input);
                    } else {
                        temp = (255 - (2 * (255 - ((getGreen(i, input) >> 1) + 64)) * (float) (255 - getGreen(i, overlay)) / 255));
                        setGreen(i, (int) temp, input);
                    }

                    if (getBlue(i, overlay) < 128) {
                        temp = (2 * ((getBlue(i, input) >> 1) + 64)) * ((float) getBlue(i, overlay) / 255);
                        setBlue(i, (int) temp, input);
                    } else {
                        temp = (255 - (2 * (255 - ((getBlue(i, input) >> 1) + 64)) * (float) (255 - getBlue(i, overlay)) / 255));
                        setBlue(i, (int) temp, input);
                    }
                }
                break;
            case ColorDodge:
                for (int i = 0; i < size; i++) {
                    if (getRed(i, overlay) == 255) {
                        setRed(i, 255, input);
                    } else {
                        int x = Math.min(255, ((getRed(i, input) << 8) / (255 - getRed(i, overlay))));
                        setRed(i, x, input);
                    }

                    if (getGreen(i, overlay) == 255) {
                        setGreen(i, 255, input);
                    } else {
                        int x = Math.min(255, ((getGreen(i, input) << 8) / (255 - getGreen(i, overlay))));
                        setGreen(i, x, input);
                    }

                    if (getBlue(i, overlay) == 255) {
                        setBlue(i, 255, input);
                    } else {
                        int x = Math.min(255, ((getBlue(i, input) << 8) / (255 - getBlue(i, overlay))));
                        setBlue(i, x, input);
                    }
                }
                break;
            case ColorBurn:
                for (int i = 0; i < size; i++) {
                    if (getRed(i, overlay) == 0) {
                        setRed(i, getRed(i, overlay), input);
                    } else {
                        int x = Math.max(0, (255 - ((255 - getRed(i, input)) << 8) / getRed(i, overlay)));
                        setRed(i, x, input);
                    }

                    if (getGreen(i, overlay) == 0) {
                        setGreen(i, getGreen(i, overlay), input);
                    } else {
                        int x = Math.max(0, (255 - ((255 - getGreen(i, input)) << 8) / getGreen(i, overlay)));
                        setGreen(i, x, input);
                    }

                    if (getBlue(i, overlay) == 0) {
                        setBlue(i, getBlue(i, overlay), input);
                    } else {
                        int x = Math.max(0, (255 - ((255 - getBlue(i, input)) << 8) / getBlue(i, overlay)));
                        setBlue(i, x, input);
                    }
                }
                break;
            case LinearLight:
                for (int i = 0; i < size; i++) {
                    int temp;

                    if (getRed(i, overlay) < 128) {
                        temp = getRed(i, input) + (2 * getRed(i, overlay));
                        if (temp < 255) {
                            setRed(i, 0, input);
                        } else {
                            setRed(i, (temp - 255), input);
                        }
                    } else {
                        int x = Math.min(getRed(i, input) + (2 * (getRed(i, overlay) - 128)), 255);
                        setRed(i, x, input);
                    }

                    if (getGreen(i, overlay) < 128) {
                        temp = getGreen(i, input) + (2 * getGreen(i, overlay));
                        if (temp < 255) {
                            setGreen(i, 0, input);
                        } else {
                            setGreen(i, (temp - 255), input);
                        }
                    } else {
                        int x = Math.min(getGreen(i, input) + (2 * (getGreen(i, overlay) - 128)), 255);
                        setGreen(i, x, input);
                    }

                    if (getBlue(i, overlay) < 128) {
                        temp = getBlue(i, input) + (2 * getBlue(i, overlay));
                        if (temp < 255) {
                            setBlue(i, 0, input);
                        } else {
                            setBlue(i, (temp - 255), input);
                        }
                    } else {
                        int x = Math.min(getBlue(i, input) + (2 * (getBlue(i, overlay) - 128)), 255);
                        setBlue(i, x, input);
                    }
                }
                break;
            case VividLight:
                for (int i = 0; i < size; i++) {
                    if (getRed(i, overlay) < 128) {
                        //Color Burn
                        int o = getRed(i, overlay) * 2;
                        if (o == 0) {
                            setRed(i, o, input);
                        } else {
                            int x = Math.max(0, (255 - ((255 - getRed(i, input)) << 8) / o));
                            setRed(i, x, input);
                        }
                    } else {
                        //Color Dodge
                        int o = 2 * (getRed(i, overlay) - 128);
                        if (o == 255) {
                            setRed(i, 255, input);
                        } else {
                            int x = Math.min(255, ((getRed(i, input) << 8) / (255 - o)));
                            setRed(i, x, input);
                        }
                    }

                    if (getGreen(i, overlay) < 128) {
                        //Color Burn
                        int o = getGreen(i, overlay) * 2;
                        if (o == 0) {
                            setGreen(i, o, input);
                        } else {
                            int x = Math.max(0, (255 - ((255 - getGreen(i, input)) << 8) / o));
                            setGreen(i, x, input);
                        }
                    } else {
                        //Color Dodge
                        int o = 2 * (getGreen(i, overlay) - 128);
                        if (o == 255) {
                            setGreen(i, 255, input);
                        } else {
                            int x = Math.min(255, ((getGreen(i, input) << 8) / (255 - o)));
                            setGreen(i, x, input);
                        }
                    }

                    if (getBlue(i, overlay) < 128) {
                        //Color Burn
                        int o = getBlue(i, overlay) * 2;
                        if (o == 0) {
                            setBlue(i, o, input);
                        } else {
                            int x = Math.max(0, (255 - ((255 - getBlue(i, input)) << 8) / o));
                            setBlue(i, x, input);
                        }
                    } else {
                        //Color Dodge
                        int o = 2 * (getBlue(i, overlay) - 128);
                        if (o == 255) {
                            setGreen(i, 255, input);
                        } else {
                            int x = Math.min(255, ((getBlue(i, input) << 8) / (255 - o)));
                            setBlue(i, x, input);
                        }
                    }
                }
                break;
            case PinLight:
                for (int i = 0; i < size; i++) {
                    int o = getRed(i, overlay) * 2;
                    if (getRed(i, overlay) < 128) {
                        //Darken
                        if (o < getRed(i, input)) {
                            setRed(i, o, input);
                        }
                    } else {
                        //Lighten
                        if (o > getRed(i, input)) {
                            setRed(i, o, input);
                        }
                    }

                    o = getGreen(i, overlay) * 2;
                    if (getGreen(i, overlay) < 128) {
                        //Darken
                        if (o < getGreen(i, input)) {
                            setGreen(i, o, input);
                        }
                    } else {
                        //Lighten
                        if (o > getGreen(i, input)) {
                            setGreen(i, o, input);
                        }
                    }

                    o = getBlue(i, overlay) * 2;
                    if (getBlue(i, overlay) < 128) {
                        //Darken
                        if (o < getBlue(i, input)) {
                            setBlue(i, o, input);
                        }
                    } else {
                        //Lighten
                        if (o > getBlue(i, input)) {
                            setBlue(i, o, input);
                        }
                    }
                }

                break;
            case Reflect:
                for (int i = 0; i < size; i++) {
                    if (getRed(i, overlay) == 255) {
                        setRed(i, 255, input);
                    } else {
                        int x = Math.min(255, (getRed(i, input) * getRed(i, input) / (255 - getRed(i, overlay))));
                        setRed(i, x, input);
                    }

                    if (getGreen(i, overlay) == 255) {
                        setGreen(i, 255, input);
                    } else {
                        int x = Math.min(255, (getGreen(i, input) * getGreen(i, input) / (255 - getGreen(i, overlay))));
                        setGreen(i, x, input);
                    }

                    if (getBlue(i, overlay) == 255) {
                        setBlue(i, 255, input);
                    } else {
                        int x = Math.min(255, (getBlue(i, input) * getBlue(i, input) / (255 - getBlue(i, overlay))));
                        setBlue(i, x, input);
                    }
                }
                break;
            case Phoenix:
                for (int i = 0; i < size; i++) {
                    int r = ((Math.min(getRed(i, input), getRed(i, overlay)) - Math.max(getRed(i, input), getRed(i, overlay)) + 255));
                    int g = ((Math.min(getGreen(i, input), getGreen(i, overlay)) - Math.max(getGreen(i, input), getGreen(i, overlay)) + 255));
                    int b = ((Math.min(getBlue(i, input), getBlue(i, overlay)) - Math.max(getBlue(i, input), getBlue(i, overlay)) + 255));

                    int rgb = ColorHelper.getRGB(r, g, b);
                    setRGB(i, rgb, input);
                }
                break;
        }
        return input;
    }


}