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

package com.octo.captcha.engine.image;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;

public class DefaultImageCaptchaEngineTest extends ImageCaptchaEngineTestAbstract  {


    CaptchaEngine buildCaptchaEngine(Object[] parameter) {
        return new ImplDefaultImageCaptchaEngine((ImageCaptchaFactory[])parameter);
    }

    private class ImplDefaultImageCaptchaEngine extends DefaultImageCaptchaEngine {
        /**
         * Default constructor : takes an array of ImageCaptchaFactories.
         */
        public ImplDefaultImageCaptchaEngine(final ImageCaptchaFactory[] factories) {
            super(factories);
        }
    }


}