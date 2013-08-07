/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word.worddecorator;

/**
 * Simple decorator that provide spelling from a word
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public class SpellerWordDecorator implements WordDecorator {

    /**
     * String that separate each charater from the word
     */
    private String separtor;

    /**
     * Constructor for the Speller Decorator. It spell a word, with a separator
     *
     * @param seprator use to separate each char, for instance : ' ' or ', ' or '; ' ...
     */
    public SpellerWordDecorator(String seprator) {
        this.separtor = seprator;
    }

    /**
     * @see com.octo.captcha.component.word.worddecorator.WordDecorator#decorateWord(java.lang.String)
     */
    public String decorateWord(String original) {
        StringBuffer chars = new StringBuffer();
        //transform the word by separating each character
        for (int i = 0; i < original.length(); i++) {
            chars.append(" ");
            chars.append(original.charAt(i));
            if (i < original.length() - 1) {
                chars.append(separtor);
            }
        }
        return chars.toString();
    }

}