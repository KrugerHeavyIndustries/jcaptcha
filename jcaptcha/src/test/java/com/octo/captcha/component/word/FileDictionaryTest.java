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


package com.octo.captcha.component.word;

import junit.framework.TestCase;

import java.util.Locale;

/**
 * <p>Description: </p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.0
 */
public class FileDictionaryTest extends TestCase {

    private FileDictionary fileDictionary;

    /**
     * Constructor for FileDictionaryTest.
     */
    public FileDictionaryTest(String name) {
        super(name);
    }

    public void setUp() {
        this.fileDictionary = new FileDictionary("toddlist");
    }

    public void testGetWordList() {
        SizeSortedWordList test = this.fileDictionary.getWordList();
        assertNotNull(test);
        String testWord = test.getNextWord(new Integer(8));
        assertNotNull(testWord);
        assertEquals(8, testWord.length());


    }

    public void testGetWordListLocale() {
        SizeSortedWordList test = this.fileDictionary.getWordList(Locale.US);
        Locale expected = Locale.US;
        assertNotNull(test);
        String testWord = test.getNextWord(new Integer(8));
        assertNotNull(testWord);
        assertEquals(8, testWord.length());
        assertEquals(expected, test.getLocale());


    }

}
