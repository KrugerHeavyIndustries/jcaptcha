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
package com.octo.captcha.engine;

import com.octo.captcha.sound.SoundCaptcha;
import com.octo.captcha.sound.SoundCaptchaFactory;

import java.util.Locale;

/**
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version $Id: MockSoundCaptchaFactory.java 322 2007-03-26 17:45:25Z antoineveret $
 */
public class MockSoundCaptchaFactory extends SoundCaptchaFactory {
    /**
     * SoundCaptcha.
     *
     * @return a Sound captcha
     */
    public SoundCaptcha getSoundCaptcha() {
        return new SoundCaptcha(null, null) {
            public Boolean validateResponse(Object response) {
                return Boolean.FALSE;
            }
        };
    }

    /**
     * a SoundCaptcha.
     *
     * @return a localized SoundCaptcha
     */
    public SoundCaptcha getSoundCaptcha(Locale locale) {
        return getSoundCaptcha();
    }


}
