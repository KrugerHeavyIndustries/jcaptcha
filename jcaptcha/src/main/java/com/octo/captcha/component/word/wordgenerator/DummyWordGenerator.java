/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word.wordgenerator;

import java.util.Locale;

/**
 * <p>Description: dummy word generator contructed with a String returning the same string, with right length</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DummyWordGenerator implements WordGenerator {

    private String word = "JCAPTCHA";

    public DummyWordGenerator(String word) {
        this.word = word == null || "".equals(word) ? this.word : word;
    }

    /**
     * Return a word of length between min and max length
     *
     * @return a String of length between min and max length
     */
    public String getWord(Integer length) {
        int mod = length.intValue() % word.length();
        String cut = "";
        int mul = (length.intValue() - mod) / word.length();
        if (mod > 0) {
            cut = word.substring(0, mod);
        }
        StringBuffer returned = new StringBuffer();
        for (int i = 0; i < mul; i++) {
            returned.append(word);
        }
        returned.append(cut);
        return returned.toString();
    }

    /**
     * Return a word of length between min and max length according to the given locale
     *
     * @param length the word length
     *
     * @return a String of length between min and max length according to the given locale
     */
    public String getWord(Integer length, Locale locale) {
        return getWord(length);
    }
}
