/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

/*
 * jcaptcha, the open source java framework for captcha definition and integration
 * copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.CaptchaException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * Pastes characters in differents height lines in the background
 *
 * @date 19 mars 2007
 * @author <a href="mailto:antoine.veret@gmail.com">Antoine Véret</a>
 */
public class NonLinearTextPaster extends AbstractTextPaster {

    public NonLinearTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            Color textColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, textColor);
    }

    public NonLinearTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            ColorGenerator colorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
    }

    public NonLinearTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
    }

    /**
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs.
     *
     * @return the final image
     *
     * @throws com.octo.captcha.CaptchaException
     *          if any exception accurs during paste routine.
     */
    public BufferedImage pasteText(final BufferedImage background,
                                   final AttributedString attributedWord) throws CaptchaException {

        BufferedImage out = copyBackground(background);
        Graphics2D g2 = pasteBackgroundAndSetTextColor(out, background);

        // convert string into a series of glyphs we can work with
        ChangeableAttributedString newAttrString = new ChangeableAttributedString(g2,
                attributedWord, 2);

        // space out the glyphs with a little kerning
        newAttrString.useMinimumSpacing(6);

        // shift string to a non-linear layout in the output image
        newAttrString.shiftBoundariesToNonLinearLayout(background.getWidth(), background.getHeight());
        
        // now draw each glyph at the appropriate spot on the image.
        if (isManageColorPerGlyph())
            newAttrString.drawString(g2, getColorGenerator());
        else
            newAttrString.drawString(g2);

        g2.dispose();
        return out;
    }
}
