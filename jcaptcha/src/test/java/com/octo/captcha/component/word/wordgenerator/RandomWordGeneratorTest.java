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
public class RandomWordGeneratorTest extends TestCase {

    private RandomWordGenerator randomWordGenerator;

    /**
     * Constructor for RandomWordGeneratorTest.
     */
    public RandomWordGeneratorTest(String name) {
        super(name);
    }

    public void setUp() {
        this.randomWordGenerator = new RandomWordGenerator("azertyuiopqsdfghjklmwxcvbn");
    }

    public void testGetWord() {
        Integer wordLength = new Integer(10);
        String pickWord = this.randomWordGenerator.getWord(wordLength, Locale.US);
        assertNotNull(pickWord);
        assertTrue(pickWord.length() > 0);
        assertEquals(wordLength.intValue(), pickWord.length());
    }

}
