/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.image;

import com.octo.captcha.Captcha;
import com.octo.captcha.CaptchaFactory;

import java.util.Locale;

/**
 * <p>Implements a ImageCaptcha Factory</p>.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public abstract class ImageCaptchaFactory implements CaptchaFactory {

    /**
     * builds a captcha.
     *
     * @return a captcha
     */
    public final Captcha getCaptcha() {
        return getImageCaptcha();
    }

    /**
     * build Localized captcha (don't forget those captcha are for human beings!).
     *
     * @return a captcha corresponding to the locale
     */
    public final Captcha getCaptcha(final Locale locale) {
        return getImageCaptcha(locale);
    }

    /**
     * ImageCaptcha.
     *
     * @return a image captcha
     */
    public abstract ImageCaptcha getImageCaptcha();

    /**
     * a ImageCaptcha.
     *
     * @return a localized ImageCaptcha
     */
    public abstract ImageCaptcha getImageCaptcha(Locale locale);

}
