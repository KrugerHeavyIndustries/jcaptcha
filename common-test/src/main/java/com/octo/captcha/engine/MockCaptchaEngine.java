/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.engine;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.MockCaptcha;
import com.octo.captcha.engine.CaptchaEngineException;

import java.util.Locale;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MockCaptchaEngine implements com.octo.captcha.engine.CaptchaEngine {
    /**
     * This return a new captcha. It may be used directly.
     *
     * @return a new Captcha
     */
    public Captcha getNextCaptcha() {
        return new MockCaptcha(null);
    }

    /**
     * This return a new captcha. It may be used directly.
     *
     * @param locale the desired locale
     *
     * @return a new Captcha
     */
    public Captcha getNextCaptcha(Locale locale) {
        return new MockCaptcha(locale);
    }

    /**
     * @return captcha factories used by this engine
     */
    public CaptchaFactory[] getFactories() {
        return new CaptchaFactory[0];
    }

    /**
     * @param factories new captcha factories for this engine
     *
     * @throws com.octo.captcha.engine.CaptchaEngineException
     *          if the factories are invalid for this engine
     */
    public void setFactories(CaptchaFactory[] factories) throws CaptchaEngineException {

    }
}
