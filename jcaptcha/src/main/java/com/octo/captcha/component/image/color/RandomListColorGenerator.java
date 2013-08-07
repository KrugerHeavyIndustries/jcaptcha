/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.component.image.color;

import com.octo.captcha.CaptchaException;

import java.awt.*;
import java.security.SecureRandom;
import java.util.Random;

/**
 * A RandomListColor returns a random color have been picked from a user defined colors list.
 *
 * @author Benoit Doumas
 * @author Chrsitian Blavier
 */
public class RandomListColorGenerator implements ColorGenerator {
    /**
     * List of colors that can be selected
     */
    private Color[] colorsList = null;

    /**
     * Use for random color selection
     */
    private Random random = new SecureRandom();

    /**
     * Constructor that take an array of Color
     *
     * @param colorsList the array of color
     */
    public RandomListColorGenerator(Color[] colorsList) {
        if (colorsList == null) {
            throw new CaptchaException("Color list cannot be null");
        }
        for (int i = 0; i < colorsList.length; i++) {
            if (colorsList[i] == null) {
                throw new CaptchaException("One or several color is null");
            }
        }
        this.colorsList = colorsList;
    }

    /**
     * @see com.octo.captcha.component.image.color.ColorGenerator#getNextColor()
     */
    public Color getNextColor() {
        int index = random.nextInt(this.colorsList.length);
        return this.colorsList[index];
    }

}
