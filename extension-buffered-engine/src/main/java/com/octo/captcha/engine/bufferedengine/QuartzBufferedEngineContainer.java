
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

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.engine.bufferedengine;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Quartz implmentation of the BufferedEngineContainer
 *
 * @author Benoit Doumas
 */
public class QuartzBufferedEngineContainer extends BufferedEngineContainer {

    private static final Log log = LogFactory.getLog(QuartzBufferedEngineContainer.class.getName());

    public QuartzBufferedEngineContainer(CaptchaEngine engine, CaptchaBuffer memoryBuffer,
                                         CaptchaBuffer diskBuffer, ContainerConfiguration containerConfiguration) {
        super(engine, memoryBuffer, diskBuffer, containerConfiguration);
    }

}
