/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.fontgenerator;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * <p>Takes a random font and apply a rotation to it. </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class DeformedRandomFontGenerator extends RandomFontGenerator {


    public DeformedRandomFontGenerator(Integer minFontSize,
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
        // rotate each letter by -0.33, +0.33 or about 20 degrees
        float theta = (myRandom.nextBoolean() ? 1 : -1) * myRandom.nextFloat() / 3;

        // private DecimalFormat debug_fmt = new DecimalFormat();
        // System.out.println("Creating " + font + " rotated angle = " + debug_fmt.format(theta * 57.2957));

        // rotate each letter by this angle
        AffineTransform at = new AffineTransform();
        at.rotate(theta, myRandom.nextDouble()/*x*/, myRandom.nextDouble()/*y*/);
        return font.deriveFont(at);
    }
}
