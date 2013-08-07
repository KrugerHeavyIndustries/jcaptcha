/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.color;

import java.awt.*;

/**
 * ColorGenerator is an interface used by TextPaster to generate color for rendering each character with specific
 * color.
 *
 * @author Benoit Doumas
 * @author Christian Blavier
 */
public interface ColorGenerator {
    /**
     * This return a new color, from a finite set.
     *
     * @return the next color
     */
    Color getNextColor();

}
