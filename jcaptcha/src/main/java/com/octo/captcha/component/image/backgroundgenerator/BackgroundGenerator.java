/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.backgroundgenerator;

import java.awt.image.BufferedImage;

/**
 * <p>Used by ComposedWordToImage to build a background image</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface BackgroundGenerator {

    /**
     * @return the generated image height
     */
    int getImageHeight();

    /**
     * @return teh generated image width
     */
    int getImageWidth();

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    BufferedImage getBackground();
}
