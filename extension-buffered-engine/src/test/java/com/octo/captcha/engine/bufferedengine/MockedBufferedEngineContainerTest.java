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

import com.octo.captcha.engine.MockCaptchaEngine;
import com.octo.captcha.engine.bufferedengine.buffer.MemoryCaptchaBuffer;

import java.util.HashMap;
import java.util.Locale;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class MockedBufferedEngineContainerTest extends BufferedEngineContainerTestAbstract {

    int swap = 100;
    int feed = 1000;
    int maxVolatil = 2 * swap;
    int maxPersistent = 3 * feed;
    BufferedEngineContainer engine;

    protected void setUp() throws Exception {
        super.setUp();
        HashMap localRatio = new HashMap();
        localRatio.put(Locale.FRANCE, new Double(0.2));
        localRatio.put(Locale.US, new Double(0.8));
        ContainerConfiguration config = new ContainerConfiguration(localRatio, maxVolatil, maxPersistent, swap, feed);
        this.engine = new MockedBufferedCaptchaEngine(config);
    }


    public void testFeedAndSwap() {
        assertEquals("buffers should be empty", 0, engine.getVolatileBuffer().size());
        assertEquals("buffers should be empty", 0, engine.getPersistentBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("buffers should be empty", 0, engine.getVolatileBuffer().size());
        assertEquals("buffers should be empty", 0, engine.getPersistentBuffer().size());
        engine.feedPersistentBuffer();
        assertEquals("volatil buffer should be empty", 0, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be filled", feed, engine.getPersistentBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("volatil buffer should be filled", swap, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", feed - swap, engine.getPersistentBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("volatil buffer should be filled", 2 * swap, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", feed - 2 * swap, engine.getPersistentBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("volatil buffer should be filled", maxVolatil, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", feed - 2 * swap, engine.getPersistentBuffer().size());
        engine.getNextCaptcha(Locale.FRANCE);
        assertEquals("volatil buffer should be updated", maxVolatil - 1, engine.getVolatileBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("volatil buffer should be filled", 2 * swap, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", feed - 2 * swap - 1, engine.getPersistentBuffer().size());
        engine.getNextCaptcha(Locale.US);
        assertEquals("volatil buffer should be updated", maxVolatil - 1, engine.getVolatileBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("volatil buffer should be filled", maxVolatil, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", feed - 2 * swap - 2, engine.getPersistentBuffer().size());
        engine.getNextCaptcha(Locale.CHINA);
        assertEquals("volatil buffer should be updated", maxVolatil, engine.getVolatileBuffer().size());
        engine.swapCaptchasFromPersistentToVolatileMemory();
        assertEquals("volatil buffer should be filled", maxVolatil, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", feed - 2 * swap - 2, engine.getPersistentBuffer().size());
        engine.feedPersistentBuffer();
        assertEquals("volatil buffer should be filled", maxVolatil, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", 2 * feed - 2 * swap - 2, engine.getPersistentBuffer().size());

        engine.feedPersistentBuffer();
        assertEquals("volatil buffer should be filled", maxVolatil, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", 3 * feed - 2 * swap - 2, engine.getPersistentBuffer().size());

        engine.feedPersistentBuffer();
        assertEquals("volatil buffer should be filled", maxVolatil, engine.getVolatileBuffer().size());
        assertEquals("pers buffer should be updated", maxPersistent, engine.getPersistentBuffer().size());


    }


    public BufferedEngineContainer getEngine() {
        return this.engine;
    }

    public void releaseEngine(BufferedEngineContainer engine) {
        this.engine.getVolatileBuffer().clear();
        this.engine.getPersistentBuffer().clear();

    }

    private class MockedBufferedCaptchaEngine extends BufferedEngineContainer {
        /**
         * Construct an BufferedEngineContainer with and Captcha engine, a memory buffer, a diskBuffer and a
         * ContainerConfiguration.
         *
         * @param containerConfiguration the container configuration
         */
        public MockedBufferedCaptchaEngine(ContainerConfiguration containerConfiguration) {
            super(new MockCaptchaEngine(), new MemoryCaptchaBuffer(), new MemoryCaptchaBuffer(), containerConfiguration);
        }


    }

}
