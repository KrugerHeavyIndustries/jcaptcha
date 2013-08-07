/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:antoine.veret@gmail.com">Antoine Véret</a>
 * @date 23 avr. 2007
 */
public class CompatibilityJDK3Test extends TestCase {

    public void testThrowException()
    {
        try {
            throw new CaptchaException();
        }
        catch (CaptchaException expected) {
            // expected 
        }
        catch (UnsupportedClassVersionError error) {
            fail(error.toString());
        }
    }
}
