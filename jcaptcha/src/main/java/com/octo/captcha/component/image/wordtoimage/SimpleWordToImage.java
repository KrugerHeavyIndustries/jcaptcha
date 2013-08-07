/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.wordtoimage;

import com.octo.captcha.CaptchaException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p>Simple image to word implementation. For eductation only, do not use it in production</p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class SimpleWordToImage extends AbstractWordToImage {

    public SimpleWordToImage() {
        super();
    }

    /**
     * @return the max word length accepted by this word2image service
     */
    public int getMaxAcceptedWordLength() {
        return 10;
    }

    /**
     * @return the min word length accepted by this word2image service
     */
    public int getMinAcceptedWordLength() {
        return 1;
    }

    /**
     * @return the max word lenght accepted by this word2image service
     * @deprecated misspelled, use {@link #getMaxAcceptedWordLength()} instead
     */
    public int getMaxAcceptedWordLenght() {
        return 10;
    }

    /**
     * @return the min word lenght accepted by this word2image service
     * @deprecated misspelled, use {@link #getMinAcceptedWordLength()} instead
     */
    public int getMinAcceptedWordLenght() {
        return 1;
    }


    /**
     * @return the generated image height
     */
    public int getImageHeight() {
        return 50;
    }

    /**
     * @return the generated image width
     */
    public int getImageWidth() {
        return 100;
    }

    /**
     * @return the min font size for the generated image
     */
    public int getMinFontSize() {
        return 10;
    }

    /**
     * Method from imageFromWord method to apply font to String. Implementations must take into account the minFontSize
     * and the MaxFontSize.
     *
     * @return a Font
     */
    public Font getFont() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAllFonts()[0];
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    public BufferedImage getBackround() {
        BufferedImage background = new BufferedImage(getImageWidth(),
                getImageHeight(), BufferedImage.TYPE_INT_RGB);
        return background;
    }

    /**
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs
     *
     * @return the final image
     *
     * @throws CaptchaException if any exception accurs during paste routine.
     */
    BufferedImage pasteText(BufferedImage background,
                            AttributedString attributedWord)
            throws CaptchaException {
        //get graphics
        Graphics graph = background.getGraphics();
        // calcul text position

        int x = (getImageWidth() - getMaxAcceptedWordLength()) / 2;
        int y = (getImageHeight() - getMinFontSize()) / 2;
        graph.drawString(attributedWord.getIterator(), x, y);
        graph.dispose();
        return background;
    }

}
