/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.engine.sound;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.engine.MockImageCaptchaFactory;
import com.octo.captcha.engine.MockSoundCaptchaFactory;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.sound.SoundCaptchaFactory;
import junit.framework.TestCase;

public abstract class SoundCaptchaEngineTestAbstract extends TestCase {

    private static final MockSoundCaptchaFactory MOCK_SOUND_CAPTCHA_FACTORY_1 = new MockSoundCaptchaFactory();
    private static final MockSoundCaptchaFactory MOCK_SOUND_CAPTCHA_FACTORY_2 = new MockSoundCaptchaFactory();

    SoundCaptchaEngine defaultSoundCaptchaEngine;
    private SoundCaptchaFactory[] factories = new SoundCaptchaFactory[]{MOCK_SOUND_CAPTCHA_FACTORY_1};
    private SoundCaptchaFactory[] otherFactories = new SoundCaptchaFactory[]{MOCK_SOUND_CAPTCHA_FACTORY_1,MOCK_SOUND_CAPTCHA_FACTORY_2};

    public void testNullOrEmptyFactorySoundCaptchaEngineConstructor() throws Exception {

        try {
            buildCaptchaEngine(null);
            fail("Cannot build with null factories");
        } catch (CaptchaEngineException e) {
        	assertNotNull(e.getMessage());
        }

        try {
            buildCaptchaEngine(new SoundCaptchaFactory[]{});
            fail("Cannot build with null factories");
        } catch (CaptchaEngineException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testNullOrEmptySetFactories() throws Exception {
        this.defaultSoundCaptchaEngine = (SoundCaptchaEngine) buildCaptchaEngine(new SoundCaptchaFactory[]{MOCK_SOUND_CAPTCHA_FACTORY_2});

        try {
            defaultSoundCaptchaEngine.setFactories(null);
            fail("cannot set null factories");
        } catch (CaptchaEngineException e) {
        	assertNotNull(e.getMessage());
        }

        try {
            defaultSoundCaptchaEngine.setFactories(new ImageCaptchaFactory[]{});
            fail("cannot set null factories");
        } catch (CaptchaEngineException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testWrongTypeSetFactories() throws Exception {
        this.defaultSoundCaptchaEngine = (SoundCaptchaEngine) buildCaptchaEngine(factories);

        try {
            defaultSoundCaptchaEngine.setFactories(new ImageCaptchaFactory[]{new MockImageCaptchaFactory()});
            fail("cannot set wrong type factories");
        } catch (CaptchaEngineException e) {
        	assertNotNull(e.getMessage());
        }
    }

    public void testSetFactories() throws Exception {
        this.defaultSoundCaptchaEngine = (SoundCaptchaEngine) buildCaptchaEngine(new MockSoundCaptchaFactory[]{MOCK_SOUND_CAPTCHA_FACTORY_1});
        assertEquals(factories[0], defaultSoundCaptchaEngine.getFactories()[0]);

        defaultSoundCaptchaEngine.setFactories(otherFactories);
        assertEquals(otherFactories[1], defaultSoundCaptchaEngine.getFactories()[1]);
    }
    
    abstract CaptchaEngine buildCaptchaEngine(Object[] parameter);
}