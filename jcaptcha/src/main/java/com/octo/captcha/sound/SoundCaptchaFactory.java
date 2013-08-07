/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.sound;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaFactory;

import java.util.Locale;

/**
 * <p/>
 * Description: </p> This class is for building a sound captcha. This class is abstract.
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin </a>
 * @version 1.0
 */
public abstract class SoundCaptchaFactory implements CaptchaFactory {

    /**
     * this method builds a capctha.
     *
     * @return a captcha.
     */
    public Captcha getCaptcha() {
        return this.getSoundCaptcha();
    }

    /**
     * this method builds a localized capctha.
     *
     * @return a captcha.
     */
    public Captcha getCaptcha(Locale locale) {
        return this.getSoundCaptcha(locale);
    }

    /**
     * this method builds a sound capctha.
     *
     * @return a sound captcha.
     */
    public abstract SoundCaptcha getSoundCaptcha();

    /**
     * this method builds a localized sound capctha.
     *
     * @return a captcha.
     */
    public abstract SoundCaptcha getSoundCaptcha(Locale locale);
}