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
public class DictionaryWordGeneratorTest extends TestCase {

    private DictionaryWordGenerator dictionaryWordGenerator;
    private static String[] wordlist = {"1", "1234", "123456", "123456789", "123"};
    private static int[] lengths = {1, 4, 6, 9, 3};
    private static Integer UNKNOWN_LENGTH = new Integer(100);

    public void setUp() {
        this.dictionaryWordGenerator = new DictionaryWordGenerator(new ArrayDictionary(wordlist));
    }

    public void testGetWordInteger() {
        for (int i = 0; i < lengths.length; i++) {
            Integer length = new Integer(lengths[i]);
            String test = this.dictionaryWordGenerator.getWord(length);
            assertNotNull(test);
            assertTrue(test.length() > 0);
            assertEquals(length.intValue(), test.length());

        }
        try {
            this.dictionaryWordGenerator.getWord(UNKNOWN_LENGTH);
            fail("Should throw a CaptchaException");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }
    }

    public void testGetWordIntegerLocale() {
        for (int i = 0; i < lengths.length; i++) {
            Integer length = new Integer(lengths[i]);
            String test = this.dictionaryWordGenerator.getWord(length, Locale.US);
            assertNotNull(test);
            assertTrue(test.length() > 0);
            assertEquals(length.intValue(), test.length());
        }
        try {
            this.dictionaryWordGenerator.getWord(UNKNOWN_LENGTH);
            fail("Should throw a CaptchaException");
        } catch (CaptchaException e) {
            assertNotNull(e.getMessage());
        }
    }


}
