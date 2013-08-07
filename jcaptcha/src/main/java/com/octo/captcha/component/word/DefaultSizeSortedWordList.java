/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.TreeMap;

/**
 * <p>Container for words that is initialized from a Dictionnary. </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DefaultSizeSortedWordList implements SizeSortedWordList {

    private TreeMap sortedWords = new TreeMap();

    private Locale locale;

    private Random myRandom = new SecureRandom();

    /**
     * A word list has to be constructed with a locale
     */
    public DefaultSizeSortedWordList(Locale locale) {
        this.locale = locale;
    }

    ;

    /**
     * Return a locale for this list
     *
     * @return th e locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Adds a word to the list
     */
    public void addWord(String word) {
        Integer length = new Integer(word.length());
        if (sortedWords.containsKey(length)) {
            ArrayList thisLengthWords = (ArrayList) sortedWords.get(length);
            thisLengthWords.add(word);
            sortedWords.put(length, thisLengthWords);
        } else {
            ArrayList thisLengthWords = new ArrayList();
            thisLengthWords.add(word);
            sortedWords.put(length, thisLengthWords);
        }
        //words.add(word);
        //lengths.add(new Integer(word.length()));

    }

    /**
     * Return the min length of contained word in this wordlist
     *
     * @return the min length of contained word in this wordlist
     */
    public Integer getMinWord() {
        return (Integer) sortedWords.firstKey();
    }

    /**
     * Return the max length of contained word in this wordlist
     *
     * @return the max length of contained word in this wordlist
     */
    public Integer getMaxWord() {
        return (Integer) sortedWords.lastKey();
    }

    /**
     * Return a word of randomly choosen of the specified length. Return null if none found
     *
     * @return a word of this length
     */
    public String getNextWord(Integer length) {
        if (sortedWords.containsKey(length)) {
            ArrayList thisLengthwords = (ArrayList) sortedWords.get(length);
            int pickAWord = myRandom.nextInt(thisLengthwords.size());
            return (String) thisLengthwords.get(pickAWord);
        } else {
            return null;
        }
    }
}
