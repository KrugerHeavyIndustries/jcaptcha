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
public class DefaultSizeSortedWordListTest extends TestCase {
    private DefaultSizeSortedWordList wordList;
    public static final String WORD1 = "test";
    public static final String WORD2 = "testtest";
    public static final String WORD3 = "te";


    /**
     * Constructor for DefaultSizeSortedWordListTest.
     */
    public DefaultSizeSortedWordListTest(String name) {
        super(name);
    }

    public void setUp() {
        this.wordList = new DefaultSizeSortedWordList(Locale.US);
        this.wordList.addWord(WORD1);
    }

    public void testGetLocale() {
        Locale expected = Locale.US;
        Locale test = this.wordList.getLocale();
        assertEquals(expected, test);
    }

    public void testAddWord() {
        String test = this.wordList.getNextWord(new Integer(WORD2.length()));
        assertNull(test);
        test = this.wordList.getNextWord(new Integer(WORD1.length()));
        assertNotNull(test);
        this.wordList.addWord(WORD2);
        test = this.wordList.getNextWord(new Integer(WORD1.length()));
        assertNotNull(test);
        test = this.wordList.getNextWord(new Integer(WORD2.length()));
        assertNotNull(test);

    }

    public void testGetNextWord() {
        String expected = WORD1;
        String test = this.wordList.getNextWord(new Integer(WORD1.length()));
        assertNotNull(test);
        assertEquals(expected, test);
        this.wordList.addWord(WORD2);
        test = this.wordList.getNextWord(new Integer(WORD2.length()));
        assertEquals(WORD2, test);
    }

    public void testGetMinWord() throws Exception {
        assertEquals(WORD1.length(), this.wordList.getMinWord().intValue());
        this.wordList.addWord(WORD2);
        assertEquals(WORD1.length(), this.wordList.getMinWord().intValue());
        this.wordList.addWord(WORD3);
        assertEquals(WORD3.length(), this.wordList.getMinWord().intValue());
    }

    public void testGetMaxWord() throws Exception {
        assertEquals(WORD1.length(), this.wordList.getMaxWord().intValue());
        this.wordList.addWord(WORD2);
        assertEquals(WORD2.length(), this.wordList.getMaxWord().intValue());
        this.wordList.addWord(WORD3);
        assertEquals(WORD2.length(), this.wordList.getMaxWord().intValue());

    }
}
