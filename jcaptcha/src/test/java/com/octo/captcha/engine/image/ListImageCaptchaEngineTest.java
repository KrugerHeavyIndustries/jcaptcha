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

public class ListImageCaptchaEngineTest extends ImageCaptchaEngineTestAbstract  {
    static public ImageCaptchaFactory[] factories;

    CaptchaEngine buildCaptchaEngine(Object[] parameter) {
        factories = (ImageCaptchaFactory[])parameter ;
        return new ImplDefaultImageCaptchaEngine();
    }

    private class ImplDefaultImageCaptchaEngine extends ListImageCaptchaEngine {

        /**
         * Default constructor : takes an array of ImageCaptchaFactories.
         */


        protected void buildInitialFactories() {
             addFactories(ListImageCaptchaEngineTest.factories);
        }
    }


}