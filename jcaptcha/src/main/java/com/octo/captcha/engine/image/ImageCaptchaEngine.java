/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.engine.image;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;

/**
 * <p>Description: abstract base class for ImageCaptcha engines</p>.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class ImageCaptchaEngine
        implements com.octo.captcha.engine.CaptchaEngine {

	protected List factories = new ArrayList();
	protected Random myRandom = new SecureRandom();

    /**
     * This method build a ImageCaptchaFactory.
     *
     * @return a CaptchaFactory
     */
    public com.octo.captcha.image.ImageCaptchaFactory getImageCaptchaFactory() {
        return (com.octo.captcha.image.ImageCaptchaFactory) factories.get(
                myRandom.nextInt(factories.size()));
    }
    
    /**
     * This method use an object parameter to build a CaptchaFactory.
     *
     * @return a CaptchaFactory
     */
    public final ImageCaptcha getNextImageCaptcha() {
        return getImageCaptchaFactory().getImageCaptcha();
    }

    /**
     * This return a new captcha. It may be used directly.
     *
     * @param locale the desired locale
     * @return a new Captcha
     */
    public ImageCaptcha getNextImageCaptcha(Locale locale) {
        return getImageCaptchaFactory().getImageCaptcha(locale);
    }

    public final Captcha getNextCaptcha() {
        return getImageCaptchaFactory().getImageCaptcha();
    }

    /**
     * This return a new captcha. It may be used directly.
     *
     * @param locale the desired locale
     * @return a new Captcha
     */
    public Captcha getNextCaptcha(Locale locale) {
        return getImageCaptchaFactory().getImageCaptcha(locale);
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
            if (!ImageCaptchaFactory.class.isAssignableFrom(factories[i].getClass())) {
                throw new CaptchaEngineException("This factory is not an image captcha factory " + factories[i].getClass());
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
}
