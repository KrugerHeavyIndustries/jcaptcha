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
package com.octo.captcha.engine;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaFactory;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * Generic captcha engine, use it as default.
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class GenericCaptchaEngine implements CaptchaEngine {


    private CaptchaFactory[] factories;
    private Random myRandom = new SecureRandom();


    /**
     * Default constructor : takes an array of ImageCaptchaFactories.
     */
    public GenericCaptchaEngine(CaptchaFactory[] factories) {
        this.factories = factories;
        if (this.factories == null || this.factories.length == 0) {
            throw new CaptchaException("GenericCaptchaEngine cannot be " +
                    "constructed with a null or empty factories array");
        }
    }


    public CaptchaFactory[] getFactories() {
        return factories;
    }

    public void setFactories(CaptchaFactory[] factories) throws CaptchaEngineException {
        if (factories == null || factories.length == 0) {
            throw new CaptchaEngineException("impossible to set null or empty factories");
        }
        this.factories = factories;
    }


    /**
     * This return a new captcha. It may be used directly.
     *
     * @return a new Captcha
     */
    public Captcha getNextCaptcha() {
        return factories[myRandom.nextInt(factories.length)].getCaptcha();
    }

    /**
     * This return a new captcha. It may be used directly.
     *
     * @param locale the desired locale
     * @return a new Captcha
     */
    public Captcha getNextCaptcha(Locale locale) {
        return factories[myRandom.nextInt(factories.length)].getCaptcha(locale);
    }
}
