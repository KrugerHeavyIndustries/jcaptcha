/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.engine.image;

import java.util.Arrays;

import com.octo.captcha.image.ImageCaptchaFactory;

/**
 * <p>This is a very simple gimpy, which is constructed from an array of Factory and randomly return one when the
 * getCaptchaFactory is called</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class DefaultImageCaptchaEngine extends ImageCaptchaEngine {

    /**
     * Default constructor : takes an array of ImageCaptchaFactories.
     */
    public DefaultImageCaptchaEngine(final ImageCaptchaFactory[] factories) {
    	checkNotNullOrEmpty(factories);
    	this.factories = Arrays.asList(factories);        
    }
}
