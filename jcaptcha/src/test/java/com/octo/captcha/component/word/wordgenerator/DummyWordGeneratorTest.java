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

package com.octo.captcha.component.word.wordgenerator;

import junit.framework.TestCase;

import java.util.Locale;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class DummyWordGeneratorTest extends TestCase {

    private DummyWordGenerator dummyWordGenerator;
    private String expectedString = "JCAPTCHA";

    public void setUp() {
        this.dummyWordGenerator = new DummyWordGenerator(this.expectedString);
    }

    public void testGetDefaultWord() {
        this.dummyWordGenerator = new DummyWordGenerator(null);
        String expected = this.expectedString;
        String word = this.dummyWordGenerator.getWord(new Integer(8));
        assertEquals(expected, word);
    }

    public void testGetWordInteger() {
        String expected = this.expectedString;
        String word = this.dummyWordGenerator.getWord(new Integer(10));
        assertEquals(expected + expected.substring(0, 2), word);
    }

    public void testGetWordIntegerLocale() {
        String expected = this.expectedString;
        String word = this.dummyWordGenerator.getWord(new Integer(10), Locale.US);
        assertEquals(expected + expected.substring(0, 2), word);
    }

}
