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

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.word.ArrayDictionary;
import junit.framework.TestCase;

import java.util.Locale;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class ComposeDictionaryWordGeneratorTest extends TestCase {

    protected ComposeDictionaryWordGenerator composeDictionaryWordGenerator;
    protected String[] wordList = {"1", "12", "123", "123456"};
    protected int[] lengths = {1, 2, 3, 6};

    protected String[] badwordList = {"a", "b", "c", "d"};
    protected int[] badlengths = {1, 1, 1, 1};

    protected String[] emptywordList = {};
    protected int emptylength = 10;

    /**
     * This method is the setup for each testcase.
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.composeDictionaryWordGenerator =
                new ComposeDictionaryWordGenerator(new ArrayDictionary(wordList));
    }

    /**
     * This testcase verify if the class works normaly.
     */
    public void testGetWordIntegerLocale() {
        for (int i = 0; i < lengths.length; i++) {
            String test = this.composeDictionaryWordGenerator.getWord(new Integer(this.lengths[i]), Locale.FRENCH);
            assertNotNull(test);
            assertTrue(test.length() > 0);
            assertEquals(lengths[i], test.length());
        }
    }

    /**
     * This testcase werify if it works with words with one letter in the dictionary.
     */
    public void testSmallWordWithOneLetter() {
        this.composeDictionaryWordGenerator =
                new ComposeDictionaryWordGenerator(new ArrayDictionary(this.badwordList));
        for (int i = 0; i < badlengths.length; i++) {
            String test = this.composeDictionaryWordGenerator.getWord(new Integer(this.badlengths[i]), Locale.FRENCH);
            assertNotNull(test);
            assertTrue(test.length() > 0);
            assertEquals(badlengths[i], test.length());
        }
    }

    /**
     * this testcase verify if it works with words with no words in the dictionnary. We verify if the CaptchaException
     * is correctly trapped.
     */
    public void testEmptyDictionnary() {
        this.composeDictionaryWordGenerator =
                new ComposeDictionaryWordGenerator(new ArrayDictionary(this.emptywordList));
        try {
            this.composeDictionaryWordGenerator.getWord(new Integer(this.emptylength), Locale.FRENCH);
            fail("Shouldn't use empty dictionnary");
        } catch (CaptchaException e) {
            assertNotNull(e);
        }
    }
}
