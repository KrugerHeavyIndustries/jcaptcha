/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;

import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p>Used by ComposedWordToImage to paste the word to be distorded on the image background</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface TextPaster {

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
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs
     *
     * @return the final image
     *
     * @throws com.octo.captcha.CaptchaException
     *          if any exception accurs during paste routine.
     */
    BufferedImage pasteText(final BufferedImage background,
                            final AttributedString attributedWord)
            throws CaptchaException;
}
