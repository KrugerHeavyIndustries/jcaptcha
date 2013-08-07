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

public class ListSoundCaptchaEngineTest extends SoundCaptchaEngineTestAbstract  {
    static public SoundCaptchaFactory[] factories;

    CaptchaEngine buildCaptchaEngine(Object[] parameter) {
        factories = (SoundCaptchaFactory[])parameter ;
        return new ImplDefaultImageCaptchaEngine();
    }

    private class ImplDefaultImageCaptchaEngine extends ListSoundCaptchaEngine {

        /**
         * Default constructor : takes an array of ImageCaptchaFactories.
         */


        protected void buildInitialFactories() {
             addFactories(ListSoundCaptchaEngineTest.factories);
        }
    }


}