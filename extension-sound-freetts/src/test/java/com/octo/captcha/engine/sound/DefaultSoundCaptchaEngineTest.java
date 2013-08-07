/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package com.octo.captcha.engine.sound;

import junit.framework.TestCase;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.engine.MockImageCaptchaFactory;
import com.octo.captcha.engine.MockSoundCaptchaFactory;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.sound.SoundCaptchaFactory;

public class DefaultSoundCaptchaEngineTest extends TestCase {

    private static final MockSoundCaptchaFactory MOCK_SOUND_CAPTCHA_FACTORY_1 = new MockSoundCaptchaFactory();
    private static final MockSoundCaptchaFactory MOCK_SOUND_CAPTCHA_FACTORY_2 = new MockSoundCaptchaFactory();

    DefaultSoundCaptchaEngine defaultSoundCaptchaEngine;
    private SoundCaptchaFactory[] factories = new SoundCaptchaFactory[]{DefaultSoundCaptchaEngineTest.MOCK_SOUND_CAPTCHA_FACTORY_1};
    private SoundCaptchaFactory[] otherFactories = new SoundCaptchaFactory[]{DefaultSoundCaptchaEngineTest.MOCK_SOUND_CAPTCHA_FACTORY_2};

    public void testNullOrEmptyFactorySoundCaptchaEngineConstructor() throws Exception {
        //
        try {
            buildCaptchaEngine(null);
            fail("Cannot build with null factories");
        } catch (CaptchaEngineException e) {

        }

        try {
            buildCaptchaEngine(new SoundCaptchaFactory[]{});
            fail("Cannot build with null factories");
        } catch (CaptchaEngineException e) {

        }

    }


    public void testNullOrEmptySetFactories() throws Exception {
        this.defaultSoundCaptchaEngine = (DefaultSoundCaptchaEngine) buildCaptchaEngine(new SoundCaptchaFactory[]{DefaultSoundCaptchaEngineTest.MOCK_SOUND_CAPTCHA_FACTORY_1});

        try {
            defaultSoundCaptchaEngine.setFactories(null);
            fail("cannot set null factories");
        } catch (CaptchaEngineException e) {

        }

        try {
            defaultSoundCaptchaEngine.setFactories(new SoundCaptchaFactory[]{});
            fail("cannot set null factories");
        } catch (CaptchaEngineException e) {

        }

    }


    public void testWrongTypeSetFactories() throws Exception {
        this.defaultSoundCaptchaEngine = (DefaultSoundCaptchaEngine) buildCaptchaEngine(factories);

        try {
            defaultSoundCaptchaEngine.setFactories(new ImageCaptchaFactory[]{new MockImageCaptchaFactory()});
            fail("cannot set wrong type factories");
        } catch (CaptchaEngineException e) {

        }

    }


    public void testSetFactories() throws Exception {
        this.defaultSoundCaptchaEngine = (DefaultSoundCaptchaEngine) buildCaptchaEngine(new MockSoundCaptchaFactory[]{DefaultSoundCaptchaEngineTest.MOCK_SOUND_CAPTCHA_FACTORY_1});
        assertEquals(factories[0], defaultSoundCaptchaEngine.getFactories()[0]);


        defaultSoundCaptchaEngine.setFactories(otherFactories);
        assertEquals(otherFactories[0], defaultSoundCaptchaEngine.getFactories()[0]);


    }


    CaptchaEngine buildCaptchaEngine(Object[] parameter) {
        return new DefaultSoundCaptchaEngineTest.ImplDefaultSoundCaptchaEngine((SoundCaptchaFactory[]) parameter);
    }

    private class ImplDefaultSoundCaptchaEngine extends DefaultSoundCaptchaEngine {
        /**
         * Default constructor : takes an array of SoundCaptchaFactories.
         */
        public ImplDefaultSoundCaptchaEngine(final SoundCaptchaFactory[] factories) {
            super(factories);
        }
    }


}
