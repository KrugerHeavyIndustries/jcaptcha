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
package com.octo.captcha.engine.bufferedengine;

import com.octo.captcha.Captcha;
import junit.framework.TestCase;

import java.util.Locale;

/**
 * @author Benoit
 */
public abstract class BufferedEngineContainerTestAbstract extends TestCase {


    /*
    * Class under test for Captcha getNextCaptcha()
    */
    public void testGetNextCaptcha() {
        BufferedEngineContainer engine = getEngine();
        assertTrue(Captcha.class.isInstance(engine.getNextCaptcha()));
        releaseEngine(engine);
    }

    /*
     * Class under test for Captcha getNextCaptcha(Locale)
     */
    public void testGetNextCaptchaLocale() {
        BufferedEngineContainer engine = getEngine();
        assertTrue(Captcha.class.isInstance(engine.getNextCaptcha(Locale.FRANCE)));
        releaseEngine(engine);
    }

    public void testSwapCaptchasFromPersistentToVolatileMemory() {
        BufferedEngineContainer engine = getEngine();
        int size = engine.getVolatileBuffer().size();

        engine.feedPersistentBuffer();

        engine.swapCaptchasFromPersistentToVolatileMemory();

        assertTrue(size < engine.getVolatileBuffer().size());

        releaseEngine(engine);
    }

    public void testFeedPersistentBuffer() {
        BufferedEngineContainer engine = getEngine();
        int size = engine.getPersistentBuffer().size();

        engine.feedPersistentBuffer();

        assertTrue(size < engine.getPersistentBuffer().size());
        releaseEngine(engine);
    }

    public abstract BufferedEngineContainer getEngine();

    public abstract void releaseEngine(BufferedEngineContainer engine);

}
