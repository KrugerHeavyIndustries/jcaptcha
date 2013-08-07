/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.wordtoimage;

import com.octo.captcha.CaptchaException;

import java.awt.image.BufferedImage;

/**
 * <p>Provides methods to tranform a word to an image</p>.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface WordToImage {

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
     * @return the generated image height
     */
    int getImageHeight();

    /**
     * @return teh generated image width
     */
    int getImageWidth();

    /**
     * @return the min font size for the generated image
     */
    int getMinFontSize();

    /**
     * Main method for this service Return an image with the specified
     *
     * @return the generated image
     *
     * @throws com.octo.captcha.CaptchaException
     *          if word is invalid or an exception occurs during the image generation
     */
    BufferedImage getImage(String word) throws CaptchaException;

}
