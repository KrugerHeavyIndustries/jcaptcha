/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p><ul><li></li></ul></p>
 *
 * @author <a href="mailto:marc.antoine.garrigue@gmail.com">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public class DecoratedRandomTextPaster extends AbstractTextPaster {

    protected final int kerning = 20;
    private TextDecorator[] decorators;

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param decorators            An array of decorators
     */
    public DecoratedRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                     ColorGenerator colorGenerator, TextDecorator[] decorators) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
        this.decorators = decorators;

    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param decorators            An array of decorators
     */
    public DecoratedRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                     ColorGenerator colorGenerator, Boolean manageColorPerGlyph, TextDecorator[] decorators) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
        this.decorators = decorators;

    }

    /**
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs
     *
     * @return the final image
     *
     * @throws com.octo.captcha.CaptchaException
     *          if any exception accurs during paste routine.
     */
    public BufferedImage pasteText(BufferedImage background, AttributedString attributedWord)
            throws CaptchaException {
        BufferedImage out = copyBackground(background);
        Graphics2D g2 = pasteBackgroundAndSetTextColor(out, background);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // this attribute doesn't do anything in JDK 1.4, but maybe it will in JDK 1.5
        // attributedString.addAttribute(TextAttribute.WIDTH, TextAttribute.WIDTH_EXTENDED);

        // convert string into a series of glyphs we can work with
        ChangeableAttributedString newAttrString = new ChangeableAttributedString(g2,
                attributedWord, kerning);

        // space out the glyphs with a little kerning
        newAttrString.useMinimumSpacing(kerning);
        // shift string to a random spot in the output imge
        newAttrString.moveToRandomSpot(background);
        // now draw each glyph at the appropriate spot on th eimage.
        if (isManageColorPerGlyph()) {
            newAttrString.drawString(g2, getColorGenerator());
        } else {
            newAttrString.drawString(g2);
        }

        //and now decorate
        if (decorators != null) {
            for (int i = 0; i < decorators.length; i++) {
                decorators[i].decorateAttributedString(g2, attributedWord, newAttrString);

            }
        }
        g2.dispose();
        return out;
    }

}
