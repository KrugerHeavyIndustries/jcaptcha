/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.sound.wordtosound;

import com.octo.captcha.CaptchaException;

import javax.sound.sampled.AudioInputStream;
import java.util.Locale;

/**
 * <p/>
 * Provides methods to tranform a word to a sound </p>.
 *
 * @author Gandin Mathieu
 * @author Doumas Benoit
 * @version 1.1
 */
public interface WordToSound {
    /**
      * @deprecated misspelled, use {@link #getMaxAcceptedWordLength()} instead
      * @return the max word lenght accepted by this word2image service
      */
     int getMaxAcceptedWordLenght();

     /**
      * @deprecated misspelled, use {@link #getMinAcceptedWordLength()} instead
      * @return the min word lenght accepted by this word2image service
      */
     int getMinAcceptedWordLenght();


     /**
      * @return the max word length accepted by this word2image service
      */
     int getMaxAcceptedWordLength();

     /**
      * @return the min word length accepted by this word2image service
      */
     int getMinAcceptedWordLength();

    /**
     * Main method for this service Return a sound with the specified word
     *
     * @param word The word to tranform into sound
     *
     * @return the generated sound
     *
     * @throws com.octo.captcha.CaptchaException
     *          if word is invalid or an exception occurs during the sound generation
     */
    AudioInputStream getSound(String word) throws CaptchaException;

    /**
     * Main method for this service Return a sound with the specified word and Locale, depending on the local a sound is
     * not the same. This is a big difference with an image.
     *
     * @param word   The word to tranform into sound
     * @param locale Locale for the sound
     *
     * @return the generated sound
     *
     * @throws com.octo.captcha.CaptchaException
     *          if word is invalid or an exception occurs during the sound generation
     */
    AudioInputStream getSound(String word, Locale locale) throws CaptchaException;
}