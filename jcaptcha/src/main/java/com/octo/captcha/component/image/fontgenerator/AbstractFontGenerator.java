/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.fontgenerator;

/**
 * <p/>
 * Base class for Font generators. Sub classes must implement the getFont() method that return a Font.</br> use
 * constructor to specify your generator properties. This base class only use two parameters, minFontSize and
 * maxFontsize wich are the size font boundaries returned by the implementation. By default minFontSize=10 and
 * maxFontSize = 14. </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public abstract class AbstractFontGenerator implements FontGenerator {

    /**
     * Min Size for the font
     */
    private int minFontSize = 10;

    /**
     * Max Size for the font
     */
    private int maxFontSize = 14;

    /**
     * Default constructor for the FontGenerator
     */
    AbstractFontGenerator(Integer minFontSize, Integer maxFontSize) {
        this.minFontSize = minFontSize != null ? minFontSize.intValue() : this.minFontSize;
        this.maxFontSize = maxFontSize != null && maxFontSize.intValue() >= this.minFontSize ? maxFontSize
                .intValue()
                : Math.max(this.maxFontSize, this.minFontSize + 1);
    }

    /**
     * @return the min font size for the generated image
     */
    public int getMinFontSize() {
        return minFontSize;
    }

    /**
     * @return the max font size for the generated image
     */
    public int getMaxFontSize() {
        return maxFontSize;
    }
}
