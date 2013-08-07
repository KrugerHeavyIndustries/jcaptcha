/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>Abstract base class for background generator.</br> Sub classes must implement the getBackground() method that
 * return a newly generated background.</br> use constructor to specify your backgroundGenerator properties. This base
 * class only use two parameter, width and height of the generated background. By default widht = 200 and height=
 * 100.</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class AbstractBackgroundGenerator
        implements BackgroundGenerator {

    private int height = 100;
    private int width = 200;

    Random myRandom = new SecureRandom();

    /**
     * Default constructor takes a width and a height of the generated backgrounds
     *
     * @param width  the backgroud width
     * @param height the backgroud height
     */
    AbstractBackgroundGenerator(Integer width, Integer height) {
        this.width = width != null ? width.intValue() : this.width;
        this.height = height != null ? height.intValue() : this.height;

    }

    /**
     * @return the generated image height
     */
    public int getImageHeight() {
        return height;
    }

    /**
     * @return teh generated image width
     */
    public int getImageWidth() {
        return width;
    }

}
