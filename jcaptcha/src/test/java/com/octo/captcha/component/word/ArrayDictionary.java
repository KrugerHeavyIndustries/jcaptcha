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

import java.util.Locale;

public class ArrayDictionary implements DictionaryReader {

    private DefaultSizeSortedWordList words;

    public ArrayDictionary(String[] words) {
        this.words = new DefaultSizeSortedWordList(Locale.US);
        for (int i = 0; i < words.length; i++) {

            String word = words[i];
            this.words.addWord(word);
        }
    }


    public SizeSortedWordList getWordList() {
        return words;
    }

    public SizeSortedWordList getWordList(Locale locale) {
        return words;
    }
}
