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

import java.util.Arrays;

import com.octo.captcha.sound.SoundCaptchaFactory;

/**
 * <p/>
 * <p/>
 * </p>
 *
 * @author Benoit Doumas
 */
public class DefaultSoundCaptchaEngine extends SoundCaptchaEngine {

    /**
     * Default constructor : takes an array of SoundCaptchaFactories.
     */
    public DefaultSoundCaptchaEngine(final SoundCaptchaFactory[] factories) {
    	checkNotNullOrEmpty(factories);
    	this.factories = Arrays.asList(factories);        
    }
}