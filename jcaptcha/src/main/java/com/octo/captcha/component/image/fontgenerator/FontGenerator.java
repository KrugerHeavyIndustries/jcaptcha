/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.fontgenerator;

import java.awt.*;

/**
 * <p>Used by ComposedWordToImage to retrieve a Font to apply to the word that will be distorded</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface FontGenerator {

    /**
     * Method from imageFromWord method to apply font to String. Implementations must take into account the minFontSize
     * and the MaxFontSize.
     *
     * @return a Font
     */
    Font getFont();

    /**
     * @return the min font size for the generated image
     */
    int getMinFontSize();

    /**
     * @return the max font size for the generated image
     */
    int getMaxFontSize();

}
