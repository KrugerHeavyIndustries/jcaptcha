/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.component.image.color.ColorGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p/>
 * Pastes two times the attributed string with a random decalage from width/20 and height/2 </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class DoubleTextPaster extends AbstractTextPaster {

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param textColor             Unique color of text
     */
    public DoubleTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            Color textColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, textColor);
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        color generator of text
     */
    public DoubleTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            ColorGenerator colorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        color generator of text
     * @param manageColorPerGlyph   Boolean to set if each glyph can have a new color from the color generator
     */
    public DoubleTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
    }

    /**
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs. Pastes two times the attributed
     * string with a random decalage from width/20 and height/2
     *
     * @return the final image
     */
    public BufferedImage pasteText(final BufferedImage background,
                                   final AttributedString attributedWord) {
        int x = (background.getWidth()) / 20;
        int y = (background.getHeight()) / 2;
        BufferedImage out = copyBackground(background);
        Graphics2D g2 = pasteBackgroundAndSetTextColor(out, background);

        //        g2.drawString(attributedWord.getIterator(), x, y);
        //        g2.drawString(attributedWord.getIterator(), x +
        //                myRandom.nextInt(5) + 5, y + myRandom.nextInt(5) + 5);
        //        g2.dispose();

        // convert string into a series of glyphs we can work with
        ChangeableAttributedString newAttrString = new ChangeableAttributedString(g2,
                attributedWord, 0);

        // space out the glyphs with a little kerning
        newAttrString.useMinimumSpacing(0);
        newAttrString.useMonospacing(0);
        // shift string to a random spot in the output imge
        newAttrString.moveTo(x, y);
        // now draw each glyph at the appropriate spot on the image.
        if (isManageColorPerGlyph()) {
            newAttrString.drawString(g2, getColorGenerator());
        } else {
            newAttrString.drawString(g2);
        }

        newAttrString.moveTo(x + myRandom.nextInt(5) + 5, y + myRandom.nextInt(5) + 5);

        //draw again
        if (isManageColorPerGlyph()) {
            newAttrString.drawString(g2, getColorGenerator());
        } else {
            newAttrString.drawString(g2);
        }
        return out;
    }
}
