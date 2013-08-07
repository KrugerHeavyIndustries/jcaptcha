/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.word.worddecorator;


/**
 * Provide method to decorate a word, that means adding semantic information around the word (like a question) or
 * transforming the word into a semantical set (like a reverese spelling). It hightlight the word for a cognitive entity
 * (human), but hide it form a logical finite engine (computer)
 *
 * @author Benoit Doumas
 * @version 1.0
 */
public interface WordDecorator {
    /**
     * Function that decorate a word with semantic information.
     *
     * @param original word
     *
     * @return the semantic infomation
     */
    public String decorateWord(String original);
}