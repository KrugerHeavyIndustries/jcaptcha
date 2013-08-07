/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.service.image;

import com.octo.captcha.service.ServiceLoadTestAbstract;

/**
 * Load test of the default Service implementation
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DefaultManageableImageCaptchaServiceLoadTest extends ServiceLoadTestAbstract {

    protected void setUp() throws Exception {
        this.service = new DefaultManageableImageCaptchaService();
    }
}
