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
package com.octo.captcha.engine.bufferedengine.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import junit.framework.TestCase;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.gimpy.BasicGimpyEngine;

/**
 * Abstract class to test Buffers
 *
 * @author Benoit Doumas
 */
public abstract class CaptchaBufferTestAbstract extends TestCase {

    protected CaptchaBuffer buffer = null;


    public static final int SIZE = 50;

    public CaptchaEngine engine = null;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        engine = new BasicGimpyEngine();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Class under test for Captcha removeCaptcha()
     */
    public void testRemoveCaptcha() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore);

        Captcha captcha = buffer.removeCaptcha();

        assertNotNull(captcha);
    }

    /*
     * Class under test for Collection removeCaptcha(int)
     */
    public void testRemoveCaptchaint() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore);

        Collection captchas = buffer.removeCaptcha(SIZE);

        assertEquals(SIZE, captchas.size());
    }

    /*
     * Class under test for void putCaptcha(Captcha)
     */
    public void testPutCaptchaCaptcha() {
        buffer = getBuffer();
        buffer.putCaptcha(engine.getNextCaptcha());

        assertEquals(1, buffer.size());
    }

    /*
     * Class under test for void putAllCaptcha(Collection)
     */
    public void testPutAllCaptchaCollection() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore);

        assertEquals(SIZE, buffer.size());
    }

    /*
     * Class under test for Captcha removeCaptcha(Locale)
     */
    public void testRemoveCaptchaLocale() {
        buffer = getBuffer();

        buffer.putCaptcha(engine.getNextCaptcha(), Locale.GERMAN);


        assertNotNull(buffer.removeCaptcha(Locale.GERMAN));
    }

    /*
     * Class under test for Collection removeCaptcha(int, Locale)
     */
    public void testRemoveCaptchaintLocale() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore, Locale.GERMAN);

        Collection captchas = buffer.removeCaptcha(SIZE, Locale.GERMAN);

        assertEquals(SIZE, captchas.size());
    }

    /*
     * Class under test for void putCaptcha(Captcha, Locale)
     */
    public void testPutCaptchaCaptchaLocale() {
        buffer = getBuffer();

        buffer.putCaptcha(engine.getNextCaptcha(), Locale.GERMAN);

        assertEquals(1, buffer.size(Locale.GERMAN));
    }

    /*
     * Class under test for void putAllCaptcha(Collection, Locale)
     */
    public void testPutAllCaptchaCollectionLocale() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore, Locale.GERMAN);

        assertEquals(SIZE, buffer.size(Locale.GERMAN));
    }

    public void testClear() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(SIZE);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore);

        buffer.clear();

        assertEquals(0, buffer.size());
    }

    public void testGetLocales() {
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(100);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore, Locale.GERMAN);
        buffer.putAllCaptcha(listToStore, Locale.US);
        buffer.putAllCaptcha(listToStore, Locale.FRANCE);

        assertEquals(3, buffer.getLocales().size());
    }

    public void testIntegrity() {
        int numToRemove = 10;
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(100);

        for (int i = 0; i < SIZE; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore);

        buffer.removeCaptcha(numToRemove);

        assertEquals(SIZE - numToRemove, buffer.size());
    }

    public void testBufferGoesEmpty() {
        int numToRemove = 10;
        buffer = getBuffer();
        ArrayList listToStore = new ArrayList(100);

        for (int i = 0; i < 1; ++i) {
            listToStore.add(engine.getNextCaptcha());
        }
        buffer.putAllCaptcha(listToStore);

        assertEquals(1, buffer.removeCaptcha(numToRemove).size());
    }

    public abstract CaptchaBuffer getBuffer();

}
