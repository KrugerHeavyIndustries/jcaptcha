/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.captchastore;

import com.octo.captcha.Captcha;

import java.util.Locale;
import java.io.Serializable;

/**
 * Composite object used as a container to store a captcha and the locale used to generate it.
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: CaptchaAndLocale.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class CaptchaAndLocale implements Serializable {
    private Captcha captcha;
    private Locale locale;

    public CaptchaAndLocale(Captcha captcha) {
        this.captcha = captcha;
    }

    public CaptchaAndLocale(Captcha captcha, Locale locale) {
        this.captcha = captcha;
        this.locale = locale;
    }

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
