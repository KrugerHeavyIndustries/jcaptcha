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

package com.octo.captcha.engine.sound;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.sound.SoundCaptchaFactory;

public class DefaultSoundCaptchaEngineTest extends SoundCaptchaEngineTestAbstract  {


    CaptchaEngine buildCaptchaEngine(Object[] parameter) {
        return new ImplDefaultSoundCaptchaEngine((SoundCaptchaFactory[])parameter);
    }

    private class ImplDefaultSoundCaptchaEngine extends DefaultSoundCaptchaEngine {
        /**
         * Default constructor : takes an array of ImageCaptchaFactories.
         */
        public ImplDefaultSoundCaptchaEngine(final SoundCaptchaFactory[] factories) {
            super(factories);
        }
    }


}