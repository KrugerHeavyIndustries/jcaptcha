/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word.wordgenerator;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.word.DefaultSizeSortedWordList;
import com.octo.captcha.component.word.DictionaryReader;
import com.octo.captcha.component.word.SizeSortedWordList;

import java.util.HashMap;
import java.util.Locale;

/**
 * <p>WordGenerator based on a dictionary. Uses a Dictionary reader to retrieve words and an WordList to store the words
 * retrieved. Be sure your dictionary contains words whose length covers the whole range specified in your factory, some
 * rutime exception will occur!</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DictionaryWordGenerator implements WordGenerator {

    private Locale defaultLocale;

    private DictionaryReader factory;

    private HashMap localizedwords = new HashMap();

    public DictionaryWordGenerator(DictionaryReader reader) {
        this.factory = reader;
        //add the default wordlist to the localisedWordList
        this.defaultLocale = factory.getWordList().getLocale();
        this.localizedwords.put(defaultLocale, factory.getWordList());
    }

    /**
     * Return a word of length between min and max length
     *
     * @return a String of length between min and max length
     */
    public final String getWord(Integer length) {
        return getWord(length, defaultLocale);
    }

    /**
     * Return a word of length between min and max length according to the given locale
     *
     * @return a String of length between min and max length according to the given locale
     */
    public String getWord(Integer length, Locale locale) {
        SizeSortedWordList words;
        words = getWordList(locale);

        String word = words.getNextWord(length);
        //check if word with the specified length exist
        if (word == null) {
            //if not see if any
            throw new CaptchaException("No word of length : " + length +
                    " exists in dictionnary! please " +
                    "update your dictionary or your range!");
        }
        return word;
    }

    final SizeSortedWordList getWordList(Locale locale) {
        SizeSortedWordList words;
        if (localizedwords.containsKey(locale)) {
            words = (DefaultSizeSortedWordList) localizedwords.get(locale);
        } else {

            words = factory.getWordList(locale);
            //add to cache
            localizedwords.put(locale, words);
        }
        return words;
    }
}
