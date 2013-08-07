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

package com.octo.captcha.sound.gimpy;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.sound.WordToSoundMock;
import junit.framework.TestCase;

public class SoundGimpyFactoryTest extends TestCase {

    public void testGimpyFactory() throws Exception {
        try {
            new GimpySoundFactory(null, null);
            fail("Test is not implemented");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }
        try {
            new GimpySoundFactory(new RandomWordGenerator("a"), null);
            fail("Test is not implemented");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }

        try {
            new GimpySoundFactory(null, new WordToSoundMock());
            fail("Test is not implemented");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }
    }

}
