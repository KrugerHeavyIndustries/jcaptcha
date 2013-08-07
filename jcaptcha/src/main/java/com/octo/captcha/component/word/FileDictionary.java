/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * <p>Implementation of the DictionaryReader interface, uses a .properties file to retrieve words and return a
 * WordList.Constructed with the name of the properties file. It uses standard java mecanism for I18N</p>
 *
 * @author <a href="mailto:mga@octo.com">Mathieu Gandin</a>
 * @version 1.1
 */
public class FileDictionary implements DictionaryReader {

    private String myBundle;

    public FileDictionary(String bundle) {
        myBundle = bundle;
    }

    public SizeSortedWordList getWordList() {
        ResourceBundle bundle = ResourceBundle.getBundle(myBundle);
        SizeSortedWordList list = generateWordList(Locale.getDefault(), bundle);
        return list;
    }

    public SizeSortedWordList getWordList(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(myBundle, locale);
        SizeSortedWordList list = generateWordList(locale, bundle);
        return list;
    }

    protected SizeSortedWordList generateWordList(Locale locale, ResourceBundle bundle) {
        DefaultSizeSortedWordList list = new DefaultSizeSortedWordList(locale);
        StringTokenizer tokenizer = new StringTokenizer(bundle.getString("words"), ";");
        int count = tokenizer.countTokens();
        for (int i = 0; i < count; i++) {
            list.addWord(tokenizer.nextToken());
        }
        return list;
    }

}
