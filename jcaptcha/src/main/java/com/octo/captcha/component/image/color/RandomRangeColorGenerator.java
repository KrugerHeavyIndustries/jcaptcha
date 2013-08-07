/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.color;

import com.octo.captcha.CaptchaException;

import java.awt.*;
import java.security.SecureRandom;
import java.util.Random;

/**
 * A RandomRangeColorGenerator returns a random color whose components (red, green, blue and alpha) have been picked
 * from user defined ranges.
 *
 * @author Benoit Doumas
 * @author Christian Blavier
 */
public class RandomRangeColorGenerator implements ColorGenerator {
    /**
     * Minimal RGB color value
     */
    public static final int MIN_COLOR_COMPONENT_VALUE = 0;

    /**
     * Maximal RGB color value
     */
    public static final int MAX_COLOR_COMPONENT_VALUE = 255;

    /**
     * Transparent alpha value
     */
    public static final int TRANSPARENT_ALPHA_COMPONENT_VALUE = 0;

    /**
     * Opaque alpha value
     */
    public static final int OPAQUE_ALPHA_COMPONENT_VALUE = 255;

    /**
     * Red component range
     */
    private int[] redComponentRange;

    /**
     * Red component range
     */
    private int[] greenComponentRange;

    /**
     * blue component range
     */
    private int[] blueComponentRange;

    /**
     * alpha component range
     */
    private int[] alphaComponentRange;

    /**
     * Use for random color selection
     */
    private Random random = new SecureRandom();

    /**
     * Constructs a randomRangeColorGenerator with all components
     */
    public RandomRangeColorGenerator(int[] redComponentRange, int[] greenComponentRange,
                                     int[] blueComponentRange, int[] alphaComponentRange) {
        validateColorComponentRange(redComponentRange);
        setRedComponentRange(redComponentRange);

        validateColorComponentRange(greenComponentRange);
        setGreenComponentRange(greenComponentRange);

        validateColorComponentRange(blueComponentRange);
        setBlueComponentRange(blueComponentRange);

        validateColorComponentRange(alphaComponentRange);
        setAlphaComponentRange(alphaComponentRange);
    }

    /**
     * Constructs a randomColorGenerator with no alpha
     */
    public RandomRangeColorGenerator(int[] redComponentRange, int[] greenComponentRange,
                                     int blueComponentRange[]) {
        this(redComponentRange, greenComponentRange, blueComponentRange, new int[]{
                OPAQUE_ALPHA_COMPONENT_VALUE, OPAQUE_ALPHA_COMPONENT_VALUE});
    }

    /**
     * Validates that the start value is lesser than the end one
     *
     * @throws CaptchaException in case of validation error
     */
    private void validateColorComponentRange(int[] colorComponentRange) throws CaptchaException {
        if (colorComponentRange.length != 2) {
            throw new CaptchaException("Range length must be 2");
        }
        if (colorComponentRange[0] > colorComponentRange[1]) {
            throw new CaptchaException("Start value of color component range is greater than end value");
        }
        validateColorComponentValue(colorComponentRange[0]);
        validateColorComponentValue(colorComponentRange[1]);
    }

    /**
     * Validates that the color component value is in a authorized range
     *
     * @throws CaptchaException in case of validation error
     */
    private void validateColorComponentValue(int colorComponentValue) throws CaptchaException {
        if (colorComponentValue < MIN_COLOR_COMPONENT_VALUE
                || colorComponentValue > MAX_COLOR_COMPONENT_VALUE) {
            throw new CaptchaException("Color component value is always between "
                    + MIN_COLOR_COMPONENT_VALUE + " and " + MAX_COLOR_COMPONENT_VALUE);
        }
    }

    /**
     * @see com.octo.captcha.component.image.color.ColorGenerator#getNextColor()
     */
    public Color getNextColor() {

        int red = getRandomInRange(redComponentRange[0], redComponentRange[1]);
        int green = getRandomInRange(greenComponentRange[0], greenComponentRange[1]);
        int blue = getRandomInRange(blueComponentRange[0], blueComponentRange[1]);
        int alpha = getRandomInRange(alphaComponentRange[0], alphaComponentRange[1]);

        return new Color(red, green, blue, alpha);
    }

    /**
     * return a random value from a range
     *
     * @param start of the range
     * @param end   of the range
     * @return a random value
     */
    private int getRandomInRange(int start, int end) {
        if (start == end) {
            return start;
        } else {
            return random.nextInt(end - start) + start;
        }
    }

    /**
     * @param alphaComponentRange The alphaComponentRange to set.
     */
    private void setAlphaComponentRange(int[] alphaComponentRange) {
        this.alphaComponentRange = alphaComponentRange;
    }

    /**
     * @param blueComponentRange The blueComponentRange to set.
     */
    private void setBlueComponentRange(int[] blueComponentRange) {
        this.blueComponentRange = blueComponentRange;
    }

    /**
     * @param greenComponentRange The greenComponentRange to set.
     */
    private void setGreenComponentRange(int[] greenComponentRange) {
        this.greenComponentRange = greenComponentRange;
    }

    /**
     * @param redComponentRange The redComponentRange to set.
     */
    private void setRedComponentRange(int[] redComponentRange) {
        this.redComponentRange = redComponentRange;
    }
}
