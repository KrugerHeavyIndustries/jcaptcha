/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word.wordgenerator;

import java.util.Locale;

/**
 * <p>This interface defines methods to retrieve random words </p>.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface WordGenerator {

    /**
     * Return a word of length between min and max length
     *
     * @return a String of length between min and max length
     */
    String getWord(Integer length);

    /**
     * Return a word of length between min and max length according to the given locale
     *
     * @param length the word length
     *
     * @return a String of length between min and max length according to the given locale
     */
    String getWord(Integer length, Locale locale);

}
