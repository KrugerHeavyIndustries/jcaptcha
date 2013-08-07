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
package com.octo.captcha.engine.sound;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.SoundCaptchaFactory;

/**
 * <p>Description: abstract base class for SoundCaptcha engines</p>.
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public abstract class SoundCaptchaEngine
        implements com.octo.captcha.engine.CaptchaEngine {

    protected List factories = new ArrayList();

    protected Random myRandom = new SecureRandom();

	
    /**
     * This return a new captcha. It may be used directly.
     *
     * @return a new Captcha
     */
    public final Captcha getNextCaptcha() {
        return getNextSoundCaptcha();
    }

    /**
     * This return a new captcha. It may be used directly.
     *
     * @param locale the desired locale
     *
     * @return a new Captcha
     */
    public final Captcha getNextCaptcha(Locale locale) {
        return getNextSoundCaptcha(locale);
    }

    /**
     * @return captcha factories used by this engine
     */
    public CaptchaFactory[] getFactories() {
        return (CaptchaFactory[]) this.factories.toArray(new CaptchaFactory[factories.size()]);
    }

    /**
     * @param factories new captcha factories for this engine
     */
    public void setFactories(CaptchaFactory[] factories) throws CaptchaEngineException {
        checkNotNullOrEmpty(factories);
        ArrayList tempFactories = new ArrayList();

        for (int i = 0; i < factories.length; i++) {
            if (!SoundCaptchaFactory.class.isAssignableFrom(factories[i].getClass())) {
                throw new CaptchaEngineException("This factory is not an sound captcha factory " + factories[i].getClass());
            }
            tempFactories.add(factories[i]);
        }

        this.factories = tempFactories;
    }

  protected void checkNotNullOrEmpty(CaptchaFactory[] factories) {
        if (factories == null || factories.length == 0) {
            throw new CaptchaEngineException("impossible to set null or empty factories");
        }
    }

    /**
     * This method build a SoundCaptchaFactory.
     *
     * @return a CaptchaFactory
     */
    public SoundCaptchaFactory getSoundCaptchaFactory() {
        return (SoundCaptchaFactory) factories.get(myRandom
                .nextInt(factories.size()));
    }

    /**
     * This method build a SoundCaptchaFactory.
     *
     * @return a SoundCaptcha
     */
    public SoundCaptcha getNextSoundCaptcha() {
        return getSoundCaptchaFactory().getSoundCaptcha();
    }

    /**
     * This method build a SoundCaptchaFactory.
     *
     * @return a SoundCaptcha
     */
    public SoundCaptcha getNextSoundCaptcha(Locale locale) {
        return getSoundCaptchaFactory().getSoundCaptcha(locale);
    }
    
}
