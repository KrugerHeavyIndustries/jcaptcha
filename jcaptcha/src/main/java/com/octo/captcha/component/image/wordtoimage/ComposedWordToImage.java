/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.wordtoimage;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.textpaster.TextPaster;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p>Base class for composed WordToImage</p> It extends the AbstractWord to image and uses three others Components :
 * <ul> <li>a FontGenerator to implement the getFont() method</li> <li>a BackgroundGenerator to implement the
 * getBackround() method</li> <li>a TextParser to implement the pasteText() method</li> </ul>
 * <p/>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class ComposedWordToImage extends AbstractWordToImage {

    private FontGenerator fontGenerator;
    private BackgroundGenerator background;
    private TextPaster textPaster;

    /**
     * @param fontGenerator a AbstractFontGenerator to implement the getFont() method
     * @param background    a AbstractBackgroundGenerator to implement the getBackround() method
     * @param textPaster    a AbstractTextParser to implement the pasteText() method
     */
    public ComposedWordToImage(FontGenerator fontGenerator,
                               BackgroundGenerator background,
                               TextPaster textPaster) {
        this.background = background;
        this.fontGenerator = fontGenerator;
        this.textPaster = textPaster;
    }

    /**
     * @deprecated
     * @return the max word length accepted by this word2image service
     */
    public int getMaxAcceptedWordLenght() {
        return textPaster.getMaxAcceptedWordLenght();
    }

    /**
     * @deprecated
     * @return the min word length accepted by this word2image service
     */
    public int getMinAcceptedWordLenght() {
        return textPaster.getMinAcceptedWordLenght();
    }



    /**
     * @return the max word length accepted by this word2image service
     */
    public int getMaxAcceptedWordLength() {
        return textPaster.getMaxAcceptedWordLength();
    }

    /**
     * @return the min word length accepted by this word2image service
     */
    public int getMinAcceptedWordLength() {
        return textPaster.getMinAcceptedWordLength();
    }

    /**
     * @return the generated image height
     */
    public int getImageHeight() {
        return background.getImageHeight();
    }

    /**
     * @return teh generated image width
     */
    public int getImageWidth() {
        return background.getImageWidth();
    }

    /**
     * @return the min font size for the generated image
     */
    public int getMinFontSize() {
        return fontGenerator.getMinFontSize();
    }

    /**
     * Method from imageFromWord method to apply font to String. Implementations must take into account the minFontSize
     * and the MaxFontSize.
     *
     * @return a Font
     */
    Font getFont() {
        return fontGenerator.getFont();
    }

    /**
     * Generates a backround image on wich text will be paste. Implementations must take into account the imageHeigt and
     * imageWidth.
     *
     * @return the background image
     */
    BufferedImage getBackround() {
        return background.getBackground();
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
        return textPaster.pasteText(background, attributedWord);
    }
}
