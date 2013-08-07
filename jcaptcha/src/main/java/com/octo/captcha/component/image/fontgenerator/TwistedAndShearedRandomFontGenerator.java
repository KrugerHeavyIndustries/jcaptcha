/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.fontgenerator;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class TwistedAndShearedRandomFontGenerator
        extends TwistedRandomFontGenerator {

    public TwistedAndShearedRandomFontGenerator(Integer minFontSize,
                                                Integer maxFontSize) {
        super(minFontSize, maxFontSize);
    }


    /**
     * Provides a way for children class to customize the generated font array
     *
     * @param font
     * @return a customized font
     */
    protected Font applyCustomDeformationOnGeneratedFont(Font font) {
        font = super.applyCustomDeformationOnGeneratedFont(font);
        double rx = myRandom.nextDouble() / 3;
        double ry = myRandom.nextDouble() / 3;
        AffineTransform at = AffineTransform.getShearInstance(rx, ry);
        return font.deriveFont(at);
    }

}
