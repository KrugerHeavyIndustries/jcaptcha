/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha;

import java.util.Locale;

/**
 * Interface for the CAPTCHA factories. Class implementing this interface has the responsability to build the Captcha. A
 * captcha factory has the reponsability to build the challenge of a captcha and to pass it to the captcha constructor.
 * Defines two methods to build and retrieve a captcha. A no paramterer method and a localized method.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface CaptchaFactory {

    /**
     * builds a captcha
     *
     * @return a captcha
     */
    com.octo.captcha.Captcha getCaptcha();

    /**
     * build Localized captcha (don't forget those captcha are for human beings!)
     *
     * @param locale the locale
     *
     * @return a captcha corresponding to the locale
     */
    com.octo.captcha.Captcha getCaptcha(Locale locale);

}
