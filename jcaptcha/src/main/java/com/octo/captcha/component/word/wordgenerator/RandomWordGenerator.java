/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word.wordgenerator;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * <p>Random word generator. must be constructed with a String containing all possible chars</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class RandomWordGenerator implements WordGenerator {

    private char[] possiblesChars;

    private Random myRandom = new SecureRandom();

    public RandomWordGenerator(String acceptedChars) {
        possiblesChars = acceptedChars.toCharArray();
    }

    /**
     * Return a word of length between min and max length
     *
     * @return a String of length between min and max length
     */
    public String getWord(Integer length) {
        StringBuffer word = new StringBuffer(length.intValue());
        for (int i = 0; i < length.intValue(); i++) {
            word.append(possiblesChars[myRandom.nextInt(possiblesChars.length)]);
        }
        return word.toString();
    }

    /**
     * Return a word of length between min and max length according to the given locale
     *
     * @param length the word length
     * @return a String of length between min and max length according to the given locale
     */
    public String getWord(Integer length, Locale locale) {
        return getWord(length);
    }

}
