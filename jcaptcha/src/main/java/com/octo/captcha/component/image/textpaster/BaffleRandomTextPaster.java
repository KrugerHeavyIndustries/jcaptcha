/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */

package com.octo.captcha.component.image.textpaster;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * <p/>
 * text paster that paint white holes on the string (erase some parts) </p> You may specify the number of holes per
 * glyph : 3 by default. You may specify the color of holes : TextColor by default.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 * @see {http://www.parc.xerox.com/research/istl/projects/captcha/default.html}
 * @deprecated use the {@link DecoratedRandomTextPaster} instead
 */
public class BaffleRandomTextPaster extends RandomTextPaster {
    /**
     * circleXRatio
     */
    private static double circleXRatio = 0.7;

    /**
     * circleYRatio
     */
    private static double circleYRatio = 0.5;

    /**
     * Number of holes per glyph. Default : 3
     */
    private Integer numberOfHolesPerGlyph = new Integer(3);

    /**
     * ColorGenerator for the holes
     */
    private ColorGenerator holesColorGenerator = null;

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param textColor             Unique color of text
     * @param holesColor            Color of the holes
     */
    public BaffleRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                  Color textColor, Integer numberOfHolesPerGlyph, Color holesColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, textColor);
        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph != null ? numberOfHolesPerGlyph
                : this.numberOfHolesPerGlyph;
        this.holesColorGenerator = new SingleColorGenerator(holesColor != null ? holesColor
                : textColor);
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param numberOfHolesPerGlyph Number of holes around glyphes
     * @param holesColor            The color for the glyphs (one color for all glyphes)
     */
    public BaffleRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                  ColorGenerator colorGenerator, Integer numberOfHolesPerGlyph, Color holesColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator);
        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph != null ? numberOfHolesPerGlyph
                : this.numberOfHolesPerGlyph;
        this.holesColorGenerator = new SingleColorGenerator(holesColor != null ? holesColor
                : colorGenerator.getNextColor());
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param manageColorPerGlyph   Boolean to set if each glyph can have a new color from the color generator
     * @param numberOfHolesPerGlyph Number of holes around glyphes
     * @param holesColor            The color for the glyphs (one color for all glyphes)
     */
    public BaffleRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                  ColorGenerator colorGenerator, Boolean manageColorPerGlyph, Integer numberOfHolesPerGlyph,
                                  Color holesColor) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph != null ? numberOfHolesPerGlyph
                : this.numberOfHolesPerGlyph;
        this.holesColorGenerator = new SingleColorGenerator(holesColor != null ? holesColor
                : colorGenerator.getNextColor());
    }

    /**
     * @param minAcceptedWordLength Max length of a word
     * @param maxAcceptedWordLength Min length of a word
     * @param colorGenerator        Color generatior for the text
     * @param manageColorPerGlyph   Boolean to set if each glyph can have a new color from the color generator
     * @param numberOfHolesPerGlyph Number of holes around the glyph
     * @param holesColorGenerator   The color genator for the glyphs (one color for all glyph)
     */
    public BaffleRandomTextPaster(Integer minAcceptedWordLength, Integer maxAcceptedWordLength,
                                  ColorGenerator colorGenerator, Boolean manageColorPerGlyph, Integer numberOfHolesPerGlyph,
                                  ColorGenerator holesColorGenerator) {
        super(minAcceptedWordLength, maxAcceptedWordLength, colorGenerator, manageColorPerGlyph);
        this.numberOfHolesPerGlyph = numberOfHolesPerGlyph != null ? numberOfHolesPerGlyph
                : this.numberOfHolesPerGlyph;
        this.holesColorGenerator = holesColorGenerator != null ? holesColorGenerator
                : colorGenerator;
    }

    /**
     * Pastes the attributed string on the backround image and return the final image. Implementation must take into
     * account the fact that the text must be readable by human and non by programs
     *
     * @return the final image
     *
     * @throws CaptchaException if any exception accurs during paste routine.
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

        g2.setColor(holesColorGenerator.getNextColor());

        for (int j = 0; j < attributedWord.getIterator().getEndIndex(); j++) {
            Rectangle2D bounds = newAttrString.getBounds(j).getFrame();
            double circleMaxSize = (double) bounds.getWidth() / 2;
            for (int i = 0; i < numberOfHolesPerGlyph.intValue(); i++) {
                double circleSize = circleMaxSize * (1 + myRandom.nextDouble()) / 2;
                double circlex = bounds.getMinX() + bounds.getWidth() * circleXRatio
                        * myRandom.nextDouble();
                double circley = bounds.getMinY() - bounds.getHeight() * circleYRatio
                        * myRandom.nextDouble();
                Ellipse2D circle = new Ellipse2D.Double(circlex, circley, circleSize, circleSize);
                g2.fill(circle);
            }
        }

        g2.dispose();
        return out;
    }

}
