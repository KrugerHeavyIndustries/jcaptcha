/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Helps to retrieve internationalized questions for captchas. Used by captcha factories.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public final class CaptchaQuestionHelper {

    /**
     * The bundle name used by this helper
     */
    public static final String BUNDLE_NAME =
            CaptchaQuestionHelper.class.getName();

    private CaptchaQuestionHelper() {
    }


    /**
     * Return a localized question for the catpcha
     *
     * @param locale the locale
     * @param key    the key to retrieve a localized question : should be the captcha name
     *
     * @return a localized question
     */
    public static String getQuestion(Locale locale, String key) {
        return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
    }

}
