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
 * @author Benoit Doumas
 * @author Christian Blavier
 */
public class RandomRangeColorGeneratorTest extends TestCase {
    private RandomRangeColorGenerator colorGenerator = null;

    public void testNominalCase() {
        int[] redRange = new int[]{36, 42};
        int[] greenRange = new int[]{57, 62};
        int[] blueRange = new int[]{10, 12};
        int[] alphaRange = new int[]{78, 132};

        colorGenerator = new RandomRangeColorGenerator(redRange, greenRange, blueRange, alphaRange);

        // due to the random factor, test is repeated several times
        for (int i = 0; i < 100; i++) {
            Color color = colorGenerator.getNextColor();

            assertTrue(color.getRed() >= redRange[0] && color.getRed() <= redRange[1]);
            assertTrue(color.getGreen() >= greenRange[0] && color.getGreen() <= greenRange[1]);
            assertTrue(color.getBlue() >= blueRange[0] && color.getBlue() <= blueRange[1]);
            assertTrue(color.getAlpha() >= alphaRange[0] && color.getAlpha() <= alphaRange[1]);
        }
    }

    public void testNominalCaseWithoutAlpha() {
        int[] redRange = new int[]{36, 42};
        int[] greenRange = new int[]{57, 62};
        int[] blueRange = new int[]{10, 12};

        colorGenerator = new RandomRangeColorGenerator(redRange, greenRange, blueRange);

        // due to the random factor, test is repeated several times
        for (int i = 0; i < 100; i++) {
            Color color = colorGenerator.getNextColor();

            assertTrue(color.getRed() >= redRange[0] && color.getRed() <= redRange[1]);
            assertTrue(color.getGreen() >= greenRange[0] && color.getGreen() <= greenRange[1]);
            assertTrue(color.getBlue() >= blueRange[0] && color.getBlue() <= blueRange[1]);
        }
    }

    public void testRangeError() {
        int[] redRange = new int[]{200, 42};
        int[] greenRange = new int[]{57, 62};
        int[] blueRange = new int[]{10, 12};

        try {
            colorGenerator = new RandomRangeColorGenerator(redRange, greenRange, blueRange);
            fail();
        } catch (CaptchaException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testRangeValueError() {
        int[] redRange = new int[]{-12, 42};
        int[] greenRange = new int[]{57, 62};
        int[] blueRange = new int[]{10, 12};

        try {
            colorGenerator = new RandomRangeColorGenerator(redRange, greenRange, blueRange);
            fail();
        } catch (CaptchaException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testClosedRange() {
        int[] redRange = new int[]{0, 0};
        int[] greenRange = new int[]{100, 100};
        int[] blueRange = new int[]{255, 255};

        colorGenerator = new RandomRangeColorGenerator(redRange, greenRange, blueRange);

        Color color = colorGenerator.getNextColor();

        assertEquals(color.getRed(), redRange[0]);
        assertEquals(color.getGreen(), greenRange[0]);
        assertEquals(color.getBlue(), blueRange[0]);
    }

}
