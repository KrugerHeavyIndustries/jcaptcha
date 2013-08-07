/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.text.math;

import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.text.TextCaptcha;
import com.octo.captcha.text.TextCaptchaFactory;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * The simpliest text captcha<br/> <b>Do not use this in production!!!</b>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MathCaptchaFactory extends TextCaptchaFactory {

    private static final String BUNDLE_QUESTION_KEY = MathCaptcha.class.getName();

    Random myRamdom = new SecureRandom();

    public MathCaptchaFactory() {
    }

    /**
     * TextCaptcha.
     *
     * @return a Text captcha
     */
    public TextCaptcha getTextCaptcha() {
        return getTextCaptcha(Locale.getDefault());
    }

    /**
     * a TextCaptcha.
     *
     * @return a localized TextCaptcha
     */
    public TextCaptcha getTextCaptcha(Locale locale) {
        //build the challenge;
        //get 2 int
        int one = myRamdom.nextInt(50);
        int two = myRamdom.nextInt(50);
        TextCaptcha captcha = new MathCaptcha(getQuestion(locale), one + "+" + two, String.valueOf(one + two));

        return captcha;
    }

    protected String getQuestion(Locale locale) {
        return CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY);
    }


}
