/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.color;

import com.octo.captcha.CaptchaException;

import java.awt.*;

/**
 * Simple color generator that always return the same color
 *
 * @author Benoit Doumas
 * @author Christian Blavier
 */
public class SingleColorGenerator implements ColorGenerator {
    /**
     * Unique color to be used
     */
    public Color color = null;

    /**
     * construct a simple color generator
     *
     * @param color Unique color to be used
     */
    public SingleColorGenerator(Color color) {
        if (color == null) {
            throw new CaptchaException("Color is null");
        }
        this.color = color;
    }

    /**
     * @see com.octo.captcha.component.image.color.ColorGenerator#getNextColor()
     */
    public Color getNextColor() {
        return color;
    }

}
