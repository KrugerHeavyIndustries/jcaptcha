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
 * Paste the text randomly on the background </p>
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class RandomTextPaster extends AbstractTextPaster {

    protected final int kerning = 20;

    protected Color[] textColors = null;


    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            Color textColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, textColor);
    }

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            Color[] textColors) {
        super(minAcceptedWordLength, maxAcceptedWordLength);
        this.textColors = textColors;
    }

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            ColorGenerator colorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);

    }

    public RandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                            ColorGenerator colorGenerator, Boolean manageColorPerGlyph) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);

    }

    /**
     * Paste the text randomly on the background.
     * <p/>
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs. <p/>
     *
     * @return the final image
     *
     * @throws com.octo.captcha.CaptchaException
     *          if any exception accurs during paste routine.
     */
    public BufferedImage pasteText(final BufferedImage background,
                                   final AttributedString attributedString) {
        BufferedImage out = copyBackground(background);
        Graphics2D g2 = pasteBackgroundAndSetTextColor(out, background);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // this doesn't do anything in JDK 1.4, but maybe it will in JDK 1.5
        // attributedString.addAttribute(TextAttribute.WIDTH, TextAttribute.WIDTH_EXTENDED);

        // convert string into a series of glyphs we can work with
        ChangeableAttributedString newAttrString = new ChangeableAttributedString(g2,
                attributedString, kerning);

        // space out the glyphs with a little kerning
        newAttrString.useMinimumSpacing(kerning);
        // shift string to a random spot in the output imge
        newAttrString.moveToRandomSpot(background);
        // now draw each glyph at the appropriate spot on the image.
        if (isManageColorPerGlyph())
            newAttrString.drawString(g2, getColorGenerator());
        else
            newAttrString.drawString(g2);

        g2.dispose();
        return out;
    }

}
