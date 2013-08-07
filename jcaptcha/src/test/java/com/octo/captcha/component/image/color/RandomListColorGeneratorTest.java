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

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.component.image.color;

import com.octo.captcha.CaptchaException;
import junit.framework.TestCase;

import java.awt.*;

/**
 * @author Benoit TODO To change the template for this generated type comment go to Window - Preferences - Java - Code
 *         Style - Code Templates
 */
public class RandomListColorGeneratorTest extends TestCase {
    private RandomListColorGenerator colorGenerator = null;

    public void testNominalCase() {
        Color[] colorList = new Color[2];

        colorList[0] = new Color(1, 1, 1, 1);
        colorList[1] = new Color(2, 2, 2, 2);

        colorGenerator = new RandomListColorGenerator(colorList);

        // due to the random factor, test is repeated several times
        for (int i = 0; i < 100; i++) {
            Color color = colorGenerator.getNextColor();

            assertTrue((color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1 && color
                    .getAlpha() == 1)
                    || (color.getRed() == 2 && color.getGreen() == 2 && color.getBlue() == 2 && color
                    .getAlpha() == 2));
        }
    }

    public void testNullColor() {
        Color[] colorList = new Color[2];

        colorList[0] = new Color(1, 1, 1, 1);
        colorList[1] = null;

        try {
            colorGenerator = new RandomListColorGenerator(colorList);
            fail();
        } catch (CaptchaException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testSingleColor() {
        Color[] colorList = new Color[1];

        colorList[0] = new Color(1, 1, 1, 1);

        for (int i = 0; i < 100; i++) {
            colorGenerator = new RandomListColorGenerator(colorList);
            Color color = colorGenerator.getNextColor();

            assertTrue((color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1 && color
                    .getAlpha() == 1));
        }
    }
}
